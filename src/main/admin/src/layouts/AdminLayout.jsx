import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";
import Adminbar from "../components/Adminbar";
import { useContext } from "react";
import { AdminContext } from "../context/AdminContext";

const AdminLayout = () => {
    const {admin} = useContext(AdminContext)
    return (
        <>
            <Navbar />

            <main className="flex min-h-screen bg-gray-100">
                <Adminbar />

                <Outlet />
            </main>
        </>
    );
};

export default AdminLayout;