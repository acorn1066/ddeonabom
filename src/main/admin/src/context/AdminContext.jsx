import { createContext, useEffect, useRef, useState } from "react";

export const AdminContext = createContext();

export const AdminProvider = ({ children }) => {
    const [loading, setLoading] = useState(false);
    const [admin, setAdmin] = useState();

    const calledRef = useRef(false);

    useEffect(() => {
        if (calledRef.current) return;
        calledRef.current = true;

        fetch('/react/admin/users', {
            method: 'GET',
            credentials: 'include',
            headers: { fetch: true }
        })
            .then(async res => {
                if (res.status === 403) {
                    alert('접근권한없다');
                    window.location.href = 'http://localhost:8080';
                    return null;
                }

                setLoading(true);
                return res.json();
            })
            .then(data => {
                if (data) setAdmin(data);
            })
            .catch(err => console.log(err));
    }, []);

    if (!loading) {
        return <h1>Loading...</h1>;
    }

    const value = { admin };

    return (
        <AdminContext.Provider value={value}>
            {children}
        </AdminContext.Provider>
    );
};