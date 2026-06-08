import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import Navbar from './components/Navbar'
import Dashboard from './pages/Dashboard'
import Members from './pages/Members'
import Posts from './pages/Posts'
import Landmark from './pages/Landmark'

const App=()=>{
  return (
    <BrowserRouter basename="/"> 
      <div className="min-h-screen bg-gray-50">
        <Navbar />
        <Routes>
          <Route path="/" element={<Navigate to="/dashboard" />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/members" element={<Members />} />
          <Route path="/posts" element={<Posts />} />
          <Route path="/landmark" element={<Landmark />} />
        </Routes>
      </div>
    </BrowserRouter>
  )
}

export default App