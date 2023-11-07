import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import StudentListComponent from './components/StudentListComponent';
import StudentCreateComponent from './components/StudentCreateComponent';
import CuotaCreateComponent from "./components/CuotaCreateComponent";
import CuotaPayComponent from "./components/CuotaPayComponent"
import CuotaListComponent from "./components/CuotaListComponent";
import UpFileComponent from "./components/UpFileComponent"
import ReportCreateComponent from "./components/ReportCreateComponent"

function App() {
  return (
      <div>
          <BrowserRouter>
              <Routes>
                  <Route path="/" element={<HomeComponent />} />
                  <Route path= "/lista-estudiantes" element={<StudentListComponent />} />
                  <Route path= "/crear-estudiante" element={<StudentCreateComponent />} />
                  <Route path= "/crear-pago" element={<CuotaCreateComponent />} />
                  <Route path= "/mostrar-pago" element={<CuotaListComponent />} />
                  <Route path= "/pagar-pago" element={<CuotaPayComponent />} />
                  <Route path= "/cargar-archivo" element={<UpFileComponent />} />
                  <Route path= "/cargar-reporte" element={<ReportCreateComponent />} />

              </Routes>
          </BrowserRouter>

      </div>
  );
}

export default App;
