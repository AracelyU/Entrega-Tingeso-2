import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent';
import StudentComponent from './components/StudentComponent';
import { ReactKeycloakProvider } from '@react-keycloak/web'
import keycloak from './keycloak'
function App() {
  return (
      <div>
        <ReactKeycloakProvider authClient={keycloak}>
          <BrowserRouter>
            <Routes>
              <Route path="/" element={<HomeComponent />} />
              <Route path= "/lista-estudiantes" element={<StudentComponent />} />


            </Routes>
          </BrowserRouter>
        </ReactKeycloakProvider>
      </div>
  );
}

export default App;
