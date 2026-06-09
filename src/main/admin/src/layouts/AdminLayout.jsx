import { Outlet } from "react-router-dom";
import Navbar from "../components/Navbar";
import Adminbar from "../components/Adminbar";

const AdminLayout = () => {
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