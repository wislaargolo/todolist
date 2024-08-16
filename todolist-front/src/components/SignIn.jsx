import { Snackbar } from "@mui/material";
import MuiAlert from '@mui/material/Alert';
import { useState } from "react";
import { useNavigate } from "react-router-dom";

import Swal from "sweetalert2";


export function SignIn() {

    const [formData, setFormData] = useState({
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
            const response = await fetch('http://localhost:8080/api/users/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    username: formData.username,
                    password: formData.password
                }),
                credentials: 'include'
            });

            if(response.ok) {
                const userId = response.headers.get('User-Id');

                if (userId) {
                    localStorage.setItem('userId', userId); 
                    localStorage.setItem('isAuthenticated', 'true');
                    console.log("entou");
                }

                Swal.fire({
                    title: 'Sucesso!',
                    text: 'Login realizado com sucesso!',
                    icon: 'success',
                    timer: 2000, 
                    showConfirmButton: false,
                    willClose: () => {
                        navigate('/home'); 
                    }
                });
                
            } else {
                const errorData = await response.json();
                setSnackbarMessage(`Erro: ${errorData.error}`);
                setSnackbarOpen(true);
            }
        } catch(error) {
            setSnackbarMessage('Erro ao realizar o login.');
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
                <h3 className="card-title text-center">Entrar</h3>
                <form onSubmit={handleSubmit}>
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
                    <button type="submit" className="btn btn-primary w-100">Entrar</button>
                </form>
                <div className="text-center mt-3">
                    <button 
                    className="btn btn-link text-decoration-none"
                    onClick={() => navigate('/register')}
                    >
                    Não possui conta? Faça o cadastro
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