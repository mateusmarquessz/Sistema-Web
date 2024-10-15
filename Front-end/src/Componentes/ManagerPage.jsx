import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import './css/ManagerPage.css';

const ManagerPage = ({ onLogout }) => {
  const [tasks, setTasks] = useState([]);
  const [users, setUsers] = useState([]);
  const [selectedUserId, setSelectedUserId] = useState('');

  useEffect(() => {
    const fetchTasksAndUsers = async () => {
      try {
        // Buscar tarefas do gestor
        const managerId = localStorage.getItem('userId');
        if (managerId) {
          const tasksResponse = await axios.get(`https://sistema-web-d4c5.onrender.com/api/tasks/manager/${managerId}`, {
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token')}`
            }
          });
          setTasks(tasksResponse.data);
        }

        // Buscar usuários
        const usersResponse = await axios.get('https://sistema-web-d4c5.onrender.com/api/users/listar', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        setUsers(usersResponse.data);
      } catch (error) {
        console.error('Erro ao buscar dados:', error);
        alert('Erro ao carregar dados.');
      }
    };

    fetchTasksAndUsers();
  }, []);

  const handleUserChange = async (e) => {
    const userId = e.target.value;
    setSelectedUserId(userId);
    try {
      const response = await axios.get(`https://sistema-web-d4c5.onrender.com/api/tasks/assigned/${userId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      setTasks(response.data);
    } catch (error) {
      console.error('Erro ao buscar tarefas do usuário:', error);
      alert('Erro ao carregar a lista de tarefas do usuário.');
    }
  };

  return (
    <div className="manager-page">
      <header className="manager-header">
        <h1>Bem-vindo, Gestor!</h1>
        <nav className="manager-nav">
          <ul>
            <li><Link to="/manage-users">Gerenciar Usuários</Link></li>
            <li><Link to="/schedule-tasks">Agendar Tarefas</Link></li>
          </ul>
          <button onClick={onLogout} className="logout-button">Sair</button>
        </nav>
      </header>
      <main className="manager-content">
        <p>Aqui você pode gerenciar usuários e agendar tarefas.</p>
        
        <div className="tasks-section">
          <h2>Tarefas</h2>
          <select className="select-user-button" onChange={handleUserChange} value={selectedUserId}>
            <option value="">Selecione um Usuário</option>
            {users.map(user => (
              <option key={user.id} value={user.id}>
                {user.fullName}
              </option>
            ))}
          </select>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Mensagem</th>
                <th>Data de Vencimento</th>
                <th>Status</th>
              </tr>
            </thead>
            <tbody>
              {tasks.map(task => (
                <tr key={task.id}>
                  <td>{task.id}</td>
                  <td>{task.message}</td>
                  <td>{task.dueDate}</td>
                  <td>{task.status}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      </main>
    </div>
  );
};

export default ManagerPage;
