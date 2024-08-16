import React, { useState, useEffect } from 'react';
import { Snackbar } from '@mui/material';
import MuiAlert from '@mui/material/Alert';
import Swal from "sweetalert2";
import { useNavigate } from 'react-router-dom';

export function TaskList() {
  const [tasks, setTasks] = useState([]);
  const [snackbarOpen, setSnackbarOpen] = useState(false);
  const [snackbarMessage, setSnackbarMessage] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const fetchTasks = async () => {
      const userId = localStorage.getItem('userId');

      if (!userId) {
        setSnackbarMessage('Usuário não autenticado!');
        setSnackbarOpen(true);
        navigate('/login');
        return;
      }

      try {
        const response = await fetch(`http://localhost:8080/api/tasks/user/${userId}`, {
          method: 'GET',
          credentials: 'include',
        });

        if (!response.ok) {
          setSnackbarMessage('Erro ao buscar as tasks.');
          setSnackbarOpen(true);
          return;
        }

        const data = await response.json();

        if (data.success) {
          setTasks(data.data);
        } else {
          throw new Error(data.message);
        }
      } catch (error) {
        setSnackbarMessage('Erro ao buscar as tasks.');
        setSnackbarOpen(true);
        console.error("Erro:", error);
      }
    };

    fetchTasks();
  }, [navigate]);


  const handleSnackbarClose = () => {
    setSnackbarOpen(false);
  };

  return (
    <div className="container mt-5">
      <h2 className="mb-4 fw-bold">TaskBoard</h2>
      <div className="d-flex flex-column">
        {tasks.map(task => (
          <div key={task.id} className="task-card p-3 mb-3 d-flex align-items-center border rounded shadow-sm bg-white">
            <input type="checkbox" className="form-check-input me-3" />
            <div className="flex-grow-1">
              <h5 className="mb-1">{task.title}</h5>
              <small className="text-muted">Data de vencimento: {task.dueDate}</small>
            </div>
            <span className="badge bg-primary text-white ms-auto">{task.priority}</span>
          </div>
        ))}
      </div>
      <Snackbar
            open={snackbarOpen}
            autoHideDuration={6000}
            onClose={handleSnackbarClose}
            anchorOrigin={{ vertical: 'bottom', horizontal: 'center' }} >
            <MuiAlert onClose={handleSnackbarClose} severity="error" sx={{ width: '100%' }}>
                {snackbarMessage}
            </MuiAlert>
      </Snackbar>
    </div>
  );
}
