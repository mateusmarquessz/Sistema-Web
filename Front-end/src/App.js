import './App.css';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import React, { useState, useEffect } from 'react';
import LoginPage from './Componentes/LoginPage';
import UserPage from './Componentes/UserPage';
import ManagerPage from './Componentes/ManagerPage';
import ManageUsersPage from './Componentes/ManageUsersPage';
import ScheduleTasksPage from './Componentes/ScheduleTasksPage';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userRole, setUserRole] = useState(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('userRole');
    if (token && role) {
      setIsAuthenticated(true);
      setUserRole(role);
    } else {
      setIsAuthenticated(false);
      setUserRole(null);
    }
  }, []);

  const handleLoginSuccess = (role, token) => {
    setIsAuthenticated(true);
    setUserRole(role);
    localStorage.setItem('token', token);
    localStorage.setItem('userRole', role); 
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    setUserRole(null);
    localStorage.removeItem('token');
    localStorage.removeItem('userRole');
  };

  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage onLoginSuccess={handleLoginSuccess} />} />
          <Route
            path="/"
            element={
              isAuthenticated ? (
                userRole === 'GESTOR' ? (
                  <ManagerPage onLogout={handleLogout} />
                ) : (
                  <div className="App">
                    <div className="content">
                      <UserPage isAuthenticated={isAuthenticated} onLogout={handleLogout} />
                    </div>
                  </div>
                )
              ) : (
                <Navigate to="/login" />
              )
            }
          />
          {isAuthenticated && userRole === 'GESTOR' && (
            <>
              <Route path="/manage-users" element={<ManageUsersPage />} />
              <Route path="/schedule-tasks" element={<ScheduleTasksPage />} />
            </>
          )}
        </Routes>
      </Router>
    </div>
  );
}

export default App;
