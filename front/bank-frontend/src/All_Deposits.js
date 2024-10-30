// AllDeposits.js
import React, { useEffect, useState } from 'react';
import Header from './Header'; // Adjust the path as needed
import { useNavigate } from 'react-router-dom'; // Import useNavigate

const AllDeposits = () => {
    const [deposits, setDeposits] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');

    const navigate = useNavigate(); // Define navigate


    useEffect(() => {
        fetch('http://localhost:8081/home')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setName(data.name);
                setSurname(data.surname);
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    useEffect(() => {
        const fetchDeposits = async () => {
            try {
                const response = await fetch('http://localhost:8083/all_deposits');
                if (!response.ok) {
                    throw new Error(`Network response was not ok: ${response.statusText}`);
                }
                const data = await response.json();
                setDeposits(data);
            } catch (error) {
                console.error('Error fetching data:', error);
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchDeposits();
    }, []);

    const leaveAccount = () => {
        navigate('/login');
    };

    const goToHome = () => {
        navigate('/home')
      }

    const createDeposit = () => {
        navigate('/create_deposit');
    };

    const allDeposits = () => {
        navigate('/all_deposits');
    };

    return (
        <>
            <Header 
                name={name} 
                surname={surname} 
                leaveAccount={leaveAccount} 
                goToHome={goToHome}
                createDeposit={createDeposit} 
                allDeposits={allDeposits} 
            />
            <div>
                <h3>All Deposits</h3>
                {loading && <p>Loading...</p>}
                {error && <p style={{ color: 'red' }}>Error: {error}</p>}
                {deposits.length > 0 ? (
                    <table style={{ borderCollapse: 'collapse', width: '100%' }}>
                        <thead>
                            <tr>
                                <th style={{ border: '1px solid black', padding: '8px' }}>Type</th>
                                <th style={{ border: '1px solid black', padding: '8px' }}>Number</th>
                                <th style={{ border: '1px solid black', padding: '8px' }}>CVV</th>
                                <th style={{ border: '1px solid black', padding: '8px' }}>Amount</th>
                                <th style={{ border: '1px solid black', padding: '8px' }}>Create Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            {deposits.map((deposit) => (
                                <tr key={deposit.id}>
                                    <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.type}</td>
                                    <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.number}</td>
                                    <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.cvv}</td>
                                    <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.amount}</td>
                                    <td style={{ border: '1px solid black', padding: '8px' }}>{new Date(deposit.createDate).toLocaleString()}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                ) : (
                    <p>No deposits found.</p>
                )}
            </div>
        </>
    );
};

export default AllDeposits;
