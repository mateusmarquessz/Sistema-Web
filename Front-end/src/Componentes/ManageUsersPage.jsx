import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './css/ManageUsersPage.css';

const ManageUsersPage = () => {
  const [userData, setUserData] = useState({
    fullName: '',
    birthDate: '',
    phone: '',
    mobile: '',
    email: '',
    address: '',
    // photo: null, // Comentando a parte da foto
    password: '',
    role: 'USUARIO'
  });

  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData({ ...userData, [name]: value });
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      // Você pode adicionar lógica para enviar o arquivo ou atualizar o estado
      console.log('Arquivo selecionado:', file);
      
      // Exemplo: Atualizando o estado com o arquivo selecionado
      // setUserData({ ...userData, photo: URL.createObjectURL(file) }); // Comentando esta linha
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const formData = new FormData();
    
    // Adicione os dados do usuário ao FormData
    for (const key in userData) {
      formData.append(key, userData[key]);
    }
  
    try {
      await axios.post('https://sistema-web-d4c5.onrender.com/api/users', formData, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'multipart/form-data'
        }
      });
      alert('Usuário criado com sucesso!');
    } catch (error) {
      console.error('Erro ao criar usuário:', error);
      alert('Erro ao criar usuário.');
    }
  };
  
  // Função para voltar à página inicial
  const handleBack = () => {
    navigate('/'); // Navega para a página inicial
  };

  return (
    <div className="manage-users-page">
      <h2 className="manage-users-h2">Cadastrar Novo Usuário</h2>
      <form className="user-form" onSubmit={handleSubmit}>
        {/* Campos do formulário */}
        <div className="form-group">
          <input
            type="text"
            name="fullName"
            value={userData.fullName}
            onChange={handleChange}
            placeholder="Nome Completo"
            required
          />
        </div>
        <div className="form-group">
          <input
            type="date"
            name="birthDate"
            value={userData.birthDate}
            onChange={handleChange}
            required
          />
        </div>
        <div className="form-group">
          <input
            type="text"
            name="phone"
            value={userData.phone}
            onChange={handleChange}
            placeholder="Telefone Fixo"
            required
          />
        </div>
        <div className="form-group">
          <input
            type="text"
            name="mobile"
            value={userData.mobile}
            onChange={handleChange}
            placeholder="Celular"
            required
          />
        </div>
        <div className="form-group">
          <input
            type="email"
            name="email"
            value={userData.email}
            onChange={handleChange}
            placeholder="E-mail"
            required
          />
        </div>
        <div className="form-group">
          <input
            type="text"
            name="address"
            value={userData.address}
            onChange={handleChange}
            placeholder="Endereço"
            required
          />
        </div>
        <div className="form-group">
          <input
            type="password"
            name="password"
            value={userData.password}
            onChange={handleChange}
            placeholder="Senha"
            required
          />
        </div>
        <div className="form-group">
          <select name="role" value={userData.role} onChange={handleChange}>
            <option value="USUARIO">Usuário</option>
            <option value="GESTOR">Gestor</option>
          </select>
        </div>
        {/* Comentando a parte da imagem */}
        {/* 
        <div className="form-group">
          <label htmlFor="photo-upload" className="file-upload-btn">
            Escolher Foto
          </label>
          <input
            id="photo-upload"
            type="file"
            name="photo"
            accept="image/*"
            onChange={handleFileChange}
          />
        </div>
        */}
        <div className="form-group">
          <button type="submit">Cadastrar Usuário</button>
        </div>
      </form>
      {/* Botão para voltar à página inicial */}
      <button className="back-button" onClick={handleBack}>
        Voltar para a Página Inicial
      </button>
    </div>
  );
};

export default ManageUsersPage;
