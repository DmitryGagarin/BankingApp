import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate} from 'react-router-dom';
import Registration from './Registration';
import Login from './Login';
import Home from './Home';
import CreateDeposit from './CreateDeposit';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Navigate to="/login" />} /> 
        <Route path="/home" element={<Home />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/login" element={<Login />} />
        <Route path="/create_deposit" element={<CreateDeposit />} />
      </Routes>
    </Router>
  );
};

export default App;