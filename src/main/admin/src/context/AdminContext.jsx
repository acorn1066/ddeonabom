import { createContext, useEffect, useRef, useState } from "react";

export const AdminContext = createContext();

export const AdminProvider = ({ children }) => {
    const [loading, setLoading] = useState(null);
    const [admin, setAdmin] = useState();

    const calledRef = useRef(false);

    useEffect(() => {
        if (calledRef.current) return;
        calledRef.current = true;

        fetch('/react/admin/users', {
            method: 'get',
            credentials: 'include',
            headers: { fetch: true }
        })
            .then(res => {
                if (res.status === 403) {
<<<<<<< Updated upstream
                    alert('접근 권한이 없습니다.');
=======
                    alert('접근 권한이 없습니다');
>>>>>>> Stashed changes
                    location.href = 'http://localhost:8080';
                    return null;
                } else{
                    setLoading(true);
                }
                return res.json();
            })
            .then(data => setAdmin(data))
            .catch(err => console.log(err))
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