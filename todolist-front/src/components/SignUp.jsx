import { Snackbar } from "@mui/material";
import MuiAlert from '@mui/material/Alert';
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import Swal from "sweetalert2";


export function SignUp() {

    const [formData, setFormData] = useState({
        name: '',
        username: '',
        password: ''
      });
    
    const navigate = useNavigate();

    const [snackbarOpen, setSnackbarOpen] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');

    const handleChange = (e) => {
    setFormData({
        ...formData,
        [e.target.name]: e.target.value
    });
    };

    const handleSubmit = async (e) => {
    e.preventDefault();

        try {
            const response = await fetch('http://localhost:8080/api/users/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData),
            });

            if(response.ok) {
                Swal.fire({
                    title: 'Sucesso!',
                    text: 'Cadastro realizado com sucesso!',
                    icon: 'success',
                    timer: 2000, 
                    showConfirmButton: false,
                    willClose: () => {
                        navigate('/login'); 
                    }
                });
                
            } else {
                const errorData = await response.json();
                setSnackbarMessage(`Erro: ${errorData.error.message}`);
                setSnackbarOpen(true);
            }
        } catch(error) {
            setSnackbarMessage('Erro ao realizar o cadastro.');
            setSnackbarOpen(true);
            console.error('Erro:', error);
        }
    
    };

    const handleSnackbarClose = () => {
        setSnackbarOpen(false);
    };
    
    return (
    <div className="container d-flex align-items-center justify-content-center vh-100">
        <div className="row justify-content-center w-100">
            <div className="col-md-6 col-lg-4">
            <div className="card">
                <div className="card-body">
                <h3 className="card-title text-center">Cadastro</h3>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                    <label htmlFor="name" className="form-label">Nome</label>
                    <input
                        type="text"
                        className="form-control"
                        id="name"
                        name="name"
                        value={formData.name}
                        onChange={handleChange}
                        required
                    />
                    </div>
                    <div className="mb-3">
                    <label htmlFor="username" className="form-label">Login</label>
                    <input
                        type="text"
                        className="form-control"
                        id="username"
                        name="username"
                        value={formData.username}
                        onChange={handleChange}
                        required
                    />
                    </div>
                    <div className="mb-3">
                    <label htmlFor="password" className="form-label">Senha</label>
                    <input
                        type="password"
                        className="form-control"
                        id="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                        required
                    />
                    </div>
                    <button type="submit" className="btn btn-primary w-100">Cadastrar</button>
                </form>
                <div className="text-center mt-3">
                    <button 
                    className="btn btn-link text-decoration-none"
                    onClick={() => navigate('/login')}
                    >
                    Já tem conta? Faça o login
                    </button>
                </div>
                </div>
            </div>
            </div>
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