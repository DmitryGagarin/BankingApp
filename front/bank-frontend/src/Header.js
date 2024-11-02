import React from 'react';
import logo from './logo.jpeg'; 

const Header = ({ name, surname, leaveAccount, goToHome, createDeposit, allDeposits }) => {
    return (
        <header style={{ backgroundColor: 'green', display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px 20px' }}>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <img src={logo} alt="Logo" style={{ height: '50px', marginRight: '20px' }} />
            </div>
            <div style={{ display: 'flex', alignItems: 'center' }}>
                <h3 style={{ margin: '0 20px', color: 'white' }}>{name} {surname}</h3>
                <button onClick={leaveAccount} style={{ margin: '0 10px' }}>Leave Account</button>
                <button onClick={goToHome} style={{ margin: '0 10px' }}>Home</button>
                <button onClick={createDeposit} style={{ margin: '0 10px' }}>Create Deposit</button>
                <button onClick={allDeposits} style={{ margin: '0 10px' }}>All Deposits</button>
            </div>
        </header>
    );
};


export default Header;
