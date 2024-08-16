import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { SignUp } from './components/SignUp';

import 'bootstrap/dist/css/bootstrap.min.css';
import './global.css';
import './App.css';
import { SignIn } from './components/SignIn';
import { TaskList } from './components/TaskList';

function App() {
  return (
    <div>
      <Router>
        <Routes>
          <Route path="/register" element={<SignUp />} />
          <Route path="/login" element={<SignIn />} />
          <Route path="/home" element={<TaskList />} />
        </Routes>
      </Router>
    </div>
  )
}

export default App
