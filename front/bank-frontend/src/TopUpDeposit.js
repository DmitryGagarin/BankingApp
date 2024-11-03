import React, { useEffect, useState } from 'react'; 
import { useNavigate } from 'react-router-dom'; 

const TopUpDeposit = () => {
    const [deposits, setDeposits] = useState([]);
    const [selectedDepositId, setSelectedDepositId] = useState('');
    const [amount, setAmount] = useState('');
    const navigate = useNavigate();

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
                console.error('Error fetching deposits:', error);
            }
        };

        fetchDeposits();
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!selectedDepositId || !amount) {
            alert('Please select a deposit and enter an amount.');
            return;
        }

        fetch(`http://localhost:8083/top-up-deposit/${selectedDepositId}/${amount}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(text => {
            alert("Amount updated successfully");
            navigate('/home');
        })
        .catch((error) => {
            console.error('Error updating deposit:', error);
            alert("No connection to backend");
        });
    };

    return (
        <div>
            <h3>Top Up Deposit</h3>
            <form onSubmit={handleSubmit}>
                <table style={{ borderCollapse: 'collapse', width: '100%' }}>
                    <thead>
                        <tr>
                            <th style={{ border: '1px solid black', padding: '8px' }}>Select</th>
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
                                <td style={{ border: '1px solid black', padding: '8px' }}>
                                    <input
                                        type="radio"
                                        value={deposit.id}
                                        checked={selectedDepositId === deposit.id}
                                        onChange={() => setSelectedDepositId(deposit.id)}
                                    />
                                </td>
                                <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.type}</td>
                                <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.number}</td>
                                <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.cvv}</td>
                                <td style={{ border: '1px solid black', padding: '8px' }}>{deposit.amount}</td>
                                <td style={{ border: '1px solid black', padding: '8px' }}>{new Date(deposit.createDate).toLocaleString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div>
                    <label>
                        Amount:
                        <input
                            type="number"
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                            required
                        />
                    </label>
                </div>
                <button type="submit">Top Up</button>
            </form>
        </div>
    );
};

export default TopUpDeposit;
