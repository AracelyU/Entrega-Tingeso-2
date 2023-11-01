import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import StudentListComponent from './components/StudentListComponent';
import StudentCreateComponent from './components/StudentCreateComponent';

function App() {
  return (
      <div>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<HomeComponent />} />
                  <Route path= "/lista-estudiantes" element={<StudentListComponent />} />
                  <Route path= "/crear-estudiante" element={<StudentCreateComponent />} />
              </Routes>
          </BrowserRouter>

      </div>
  );
}

export default App;
