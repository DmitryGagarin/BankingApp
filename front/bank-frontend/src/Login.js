import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom'; 

const Login = () => {
    const [formData, setFormData] = useState({
        email: '',
        password: '',
    });

    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const validateForm = () => {
        const newErrors = {};
        if (!formData.email) newErrors.email = 'Email is required.';
        if (!formData.password) newErrors.password = 'Password is required.';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateForm()) {
            console.log('Login attempt:', formData);
            fetch(`http://localhost:8081/login/${formData.email}/${formData.password}`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.text();
            })
            .then(text => {
                console.log('Response text:', text);
                try {
                    // Attempt to parse the response as JSON
                    const data = JSON.parse(text);
                    alert("Login successful");
                    console.log('Auth Successful:', data);
                    navigate('/home');                
                } catch (parseError) {
                    // If parsing fails, handle it as a plain text response
                    console.log('Received plain text response:', text);
                    alert(text); // Alert the plain text message
                    navigate('/home');                
                }
            })
            .catch((error) => {
                console.error('Error during authentication:', error);
                alert("No connection to backend");
            });
        }
    };

    const redirectToRegistration = () => {
        navigate('/registration'); 
    }

    return (
        <>
            <h1>Login Page</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                    />
                    {errors.email && <p>{errors.email}</p>}
                </div>

                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                    {errors.password && <p>{errors.password}</p>}
                </div>

                <button type="submit">Login</button>
            </form>
            <br></br>
            <h2>
                <button type="submit" name='Already_registered' onClick={redirectToRegistration}>Create Account</button>
            </h2>
        </>
    );
};

export default Login;
