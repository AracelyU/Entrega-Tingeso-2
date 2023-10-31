import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import StudentComponent from './components/StudentComponent';
function App() {
  return (
      <div>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<HomeComponent />} />
                  <Route path= "/lista-estudiantes" element={<StudentComponent />} />
              </Routes>
          </BrowserRouter>

      </div>
  );
}

export default App;
