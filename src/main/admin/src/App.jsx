import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import AdminLayout from "./layouts/AdminLayout";

import Dashboard from "./pages/Dashboard";
import Members from "./pages/Members";
import Posts from "./pages/Posts";
import Landmark from "./pages/Landmark";
import Notice from "./pages/Notice";
import Report from "./pages/Report";
import NoticeWrite from "./pages/NoticeWrite";
import NoticeEdit from "./pages/NoticeEdit";


const App = () => {
    return (
        <BrowserRouter>
            <Routes>

                <Route path="/" element={<Navigate to="/dashboard" />} />

                <Route element={<AdminLayout />}>
                    <Route path="/dashboard" element={<Dashboard />} />
                    <Route path="/notice" element={<Notice />} />
                    <Route path="/members" element={<Members />} />
                    <Route path="/posts" element={<Posts />} />
                    <Route path="/report" element={<Report />} />
                    <Route path="/landmark" element={<Landmark />} />

                    <Route path="/notice/write" element={<NoticeWrite />} />
                    <Route path="/notice/edit/:noticeNo" element={<NoticeEdit />} />
                </Route>

            </Routes>
        </BrowserRouter>
    );
};

export default App;