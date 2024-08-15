import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { SignUp } from './components/SignUp';

import 'bootstrap/dist/css/bootstrap.min.css';
import './global.css';
import './App.css';
import { SignIn } from './components/SignIn';

function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/register" element={<SignUp />} />
          <Route path="/login" element={<SignIn />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App
