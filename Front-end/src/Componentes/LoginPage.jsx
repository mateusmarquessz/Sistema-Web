import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './css/LoginPage.css';

const LoginPage = ({ onLoginSuccess }) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const [error, setError] = useState('');

  const handleLogin = async (e) => {
    e.preventDefault();
  
    try {
      //const response = await axios.post("http://localhost:8080/auth/login", {
        //email,
        //password,
      //});
      const response = await axios.post("https://sistema-web-d4c5.onrender.com/10000/auth/login", {
        email,
        password,
      });
      
  
      localStorage.setItem("token", response.data.token);
      onLoginSuccess(response.data.role, response.data.token);
      navigate('/');
    } catch (error) {
      if (error.response) {
        // O servidor respondeu com um status diferente de 2xx
        setError(error.response.data.message || 'Erro ao fazer login');
      } else if (error.request) {
        // A requisição foi feita, mas não houve resposta
        setError('Erro de comunicação com o servidor');
      } else {
        // Outro erro relacionado à configuração da requisição
        setError('Ocorreu um erro inesperado. Tente novamente.');
      }
      console.error('Erro ao fazer login:', error);
    }
  };
  

  return (
    <div className="login-container">
      <h2>Login</h2>
      <form onSubmit={handleLogin} className="login-form">
        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Senha:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {error && <div className="error-message">{error}</div>}
        <button type="submit" id="login" className="login-button">
          Login
        </button>
      </form>
    </div>
  );
};

export default LoginPage;