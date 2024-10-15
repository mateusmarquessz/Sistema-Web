import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './css/UserPage.css';
import userIcon from './assets/img/usericon.png';

const UserPage = () => {
  const [user, setUser] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [filteredTasks, setFilteredTasks] = useState([]);
  const [filter, setFilter] = useState('todos');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const token = localStorage.getItem('token');
        if (!token) {
          throw new Error('No token found');
        }

        const userResponse = await axios.get('https://sistema-web-d4c5.onrender.com/api/users/me', {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        setUser(userResponse.data);

        const tasksResponse = await axios.get(`https://sistema-web-d4c5.onrender.com/api/tasks/assigned/${userResponse.data.id}`, {
          headers: {
            'Authorization': `Bearer ${token}`,
          },
        });

        setTasks(tasksResponse.data);
        setFilteredTasks(tasksResponse.data);
        
        console.log("Tarefas recebidas:", tasksResponse.data);
      } catch (err) {
        setError(err.response?.data?.message || 'Failed to fetch user data');
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, []);

  useEffect(() => {
    const filtered = filter === 'todos'
      ? tasks
      : tasks.filter(task => task.status === filter); 
  
    console.log("Tarefas filtradas:", filtered);
    console.log("Tarefas recebidas:", tasks);
    console.log("Status das tarefas:", tasks.map(task => task.status));

    setFilteredTasks(filtered);
  }, [filter, tasks]);

  const onLogout = () => {
    if (window.confirm('Tem certeza que deseja sair?')) {
      localStorage.removeItem('token');
      setUser(null);
      setTasks([]);
      setFilteredTasks([]);
      window.location.href = '/login';
    }
  };

  const completeTask = async (taskId) => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No token found');
      }
  
      await axios.patch(`https://sistema-web-d4c5.onrender.com/api/tasks/${taskId}/status`, null, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
  
      await fetchTasks();
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to complete task');
    }
  };
  
  const fetchTasks = async () => {
    try {
      const token = localStorage.getItem('token');
      if (!token) {
        throw new Error('No token found');
      }
  
      const tasksResponse = await axios.get(`https://sistema-web-d4c5.onrender.com/api/tasks/assigned/${user.id}`, {
        headers: {
          'Authorization': `Bearer ${token}`,
        },
      });
  
      setTasks(tasksResponse.data);
      setFilteredTasks(tasksResponse.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to fetch tasks');
    }
  };
  
  return (
    <div className="user-page">
      <header className="user-header">
        <div className="user-profile">
          <img
            src={user?.imageUrl || userIcon}
            alt="Profile"
            className="user-avatar"
          />
          <h1>Bem-vindo, {user ? user.fullName : 'Carregando...'}</h1>
        </div>
        <nav className="user-nav">
          <button onClick={onLogout} className="logout-button">Sair</button>
        </nav>
      </header>
      <main className="user-content">
        <p>Aqui você pode gerenciar suas tarefas.</p>
        
        <div className="filter-section">
          <label>
            Filtrar por:
            <select className="select-user-button" value={filter} onChange={(e) => setFilter(e.target.value)}>
              <option value="todos">Todos</option>
              <option value="CONCLUIDA">Concluídas</option>
              <option value="PENDENTE">Pendentes</option>
            </select>
          </label>
        </div>
        
        <div className="tasks-section">
          <h2>Tarefas</h2>
          {loading ? (
            <p>Carregando...</p>
          ) : error ? (
            <p>{error}</p>
          ) : filteredTasks.length > 0 ? (
            <div className="table-responsive"> {/* Contêiner para rolagem */}
              <table>
                <thead>
                  <tr className="table-tr">
                    <th>Mensagem</th>
                    <th>Data de Vencimento</th>
                    <th>Status</th>
                    <th>Ações</th>
                  </tr>
                </thead>
                <tbody>
                  {filteredTasks.map(task => (
                    <tr className="table-tr" key={task.id}>
                      <td>{task.message}</td>
                      <td>{task.dueDate}</td>
                      <td>{task.status}</td>
                      <td>
                        {task.status !== 'CONCLUIDA' && (
                          <button className='conclude-button' onClick={() => completeTask(task.id)}>Concluir</button>
                        )}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          ) : (
            <p>Nenhuma tarefa encontrada.</p>
          )}
        </div>
      </main>
    </div>
  );
};

export default UserPage;
