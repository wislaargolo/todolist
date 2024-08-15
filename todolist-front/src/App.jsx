import { useState } from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { SignUp } from './components/SignUp';

import 'bootstrap/dist/css/bootstrap.min.css';
import './global.css';
import './App.css';

function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/register" element={<SignUp />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App
