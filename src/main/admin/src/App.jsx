import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import AdminLayout from "./layouts/AdminLayout";

import Dashboard from "./pages/Dash";
import Members from "./pages/Members";
import Posts from "./pages/Posts";
import Landmark from "./pages/Landmark";
import Notice from "./pages/Notice";
import Report from "./pages/Report";


const App = () => {
    return (
        <BrowserRouter>
            <Routes>

                <Route path="/" element={<Navigate to="/dashboard" />} />

                <Route element={<AdminLayout />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/Notice" element={<Notice />} />
                    <Route path="/members" element={<Members />} />
                    <Route path="/posts" element={<Posts />} />
                    <Route path="/report" element={<Report />} />
                    <Route path="/landmark" element={<Landmark />} />
                </Route>

            </Routes>
        </BrowserRouter>
    );
};

export default App;