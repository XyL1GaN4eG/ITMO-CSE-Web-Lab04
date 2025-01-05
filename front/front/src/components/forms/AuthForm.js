import React from 'react';

const AuthForm = ({
                      username,
                      setUsername,
                      password,
                      setPassword,
                      handleSubmit,
                      buttonText,
                      redirectText,
                      onRedirect,
                  }) => (
    <form onSubmit={handleSubmit}>
        <input
            type="text"
            placeholder="Логин"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
        />
        <input
            type="password"
            placeholder="Пароль"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
        />
        <button type="submit">{buttonText}</button>
        {redirectText && onRedirect && (
            <button type="button" onClick={onRedirect}>
                {redirectText}
            </button>
        )}
    </form>
);

export default AuthForm;
