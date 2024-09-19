import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './css/UserPage.css';

const UserPage = () => {
  const [user, setUser] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('No token found');
        }

        const userResponse = await axios.get('http://localhost:8080/api/users/me', {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        setUser(userResponse.data);

        const tasksResponse = await axios.get(`http://localhost:8080/api/tasks/assigned/${userResponse.data.id}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        setTasks(tasksResponse.data);
      } catch (err) {
        setError(err.response?.data?.message || 'Failed to fetch user data');
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  const onLogout = () => {
    localStorage.removeItem('token');
    window.location.href = '/login';
  };

  const completeTask = async (taskId) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No token found');
      }

      await axios.patch(`http://localhost:8080/api/tasks/${taskId}/status`, null, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
      setTasks(tasks.filter(task => task.id !== taskId));
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to complete task');
    }
  };

  return (
    <div className="user-page">
      <header className="user-header">
        <div className="user-profile">
          <img src={user?.imageUrl || '/default-avatar.png'} alt="Profile" className="user-avatar" />
          <h1>Bem-vindo, {user ? user.fullName : 'Carregando...'}</h1>
        </div>
        <nav className="user-nav">
          <button onClick={onLogout} className="logout-button">Sair</button>
        </nav>
      </header>
      <main className="user-content">
        <p>Aqui você pode gerenciar suas tarefas.</p>
        
        <div className="tasks-section">
          <h2>Tarefas</h2>
          {loading ? (
            <p>Carregando...</p>
          ) : error ? (
            <p>{error}</p>
          ) : tasks.length > 0 ? (
            <table>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Mensagem</th>
                  <th>Data de Vencimento</th>
                  <th>Status</th>
                  <th>Ações</th>
                </tr>
              </thead>
              <tbody>
                {tasks.map(task => (
                  <tr key={task.id}>
                    <td>{task.id}</td>
                    <td>{task.message}</td>
                    <td>{task.dueDate}</td>
                    <td>{task.status}</td>
                    <td>
                      {task.status !== 'Concluída' && (
                        <button className='conclude-button' onClick={() => completeTask(task.id)}>Concluir</button>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>Nenhuma tarefa encontrada.</p>
          )}
        </div>
      </main>
    </div>
  );
};

export default UserPage;
