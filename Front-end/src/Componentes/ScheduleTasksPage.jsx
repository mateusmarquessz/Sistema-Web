import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './css/ScheduleTasksPage.css';

const ScheduleTasksPage = () => {
  const [taskData, setTaskData] = useState({
    message: '',
    dueDate: '',
    status: 'PENDENTE',
    assignedUserId: '',
    managerId: '',
  });

  const [users, setUsers] = useState([]); // Estado para armazenar a lista de usuários
  const navigate = useNavigate();


  //Buscar lista de usuarios
  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await axios.get('https://sistema-web-d4c5.onrender.com/api/users/listar', {
          headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
        setUsers(response.data);
      } catch (error) {
        console.error('Erro ao buscar usuários:', error);
        alert('Erro ao carregar a lista de usuários.');
      }
    };

    fetchUsers();
  }, []);

  // Função para voltar à página inicial
  const handleBack = () => {
    navigate('/'); // Navega para a página inicial
  };
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setTaskData({ ...taskData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('https://sistema-web-d4c5.onrender.com/api/tasks', taskData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      alert('Tarefa agendada com sucesso!');
      
    } catch (error) {
      console.error('Erro ao agendar tarefa:', error);
      alert('Erro ao agendar tarefa.');
    }
  };

  return (
    <div className="schedule-tasks-page">
      <h2 className="schedule-h2">Agendar Nova Tarefa</h2>
        <form className="task-form" onSubmit={handleSubmit}>
          <div className="form-group">
            <textarea
              name="message"
              value={taskData.message}
              onChange={handleChange}
              placeholder="Mensagem da Tarefa"
              required
            />
          </div>
          <div className="form-group">
            <input
              type="date"
              name="dueDate"
              value={taskData.dueDate}
              onChange={handleChange}
              required
            />
          </div>
          <div className="form-group">
            <select
              name="assignedUserId"
              value={taskData.assignedUserId}
              onChange={handleChange}
              required
            >
              <option value="">Selecione um usuário</option>
              {users.map((user) => (
                <option key={user.id} value={user.id}>
                  {user.fullName}
                </option>
              ))}
            </select>
          </div>
          <div className="form-group">
          <button type="submit">Agendar Tarefa</button>
          </div>
        </form>

      <button className="back-button" onClick={handleBack}>
        Voltar para a Página Inicial
      </button>
    </div>
  );
};

export default ScheduleTasksPage;
