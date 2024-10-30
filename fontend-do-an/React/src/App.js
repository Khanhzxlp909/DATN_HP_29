import logo from './logo.svg';
import './App.css';


function App() {
   const age = 18;
   const name = "Eat3torice";
   const isFemale = true;
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Eat3torice
        </p>
        <p> Xin chao {name} dep trai - tuoi {age} -{isFemale ? 'Male' : 'Female'}</p>

        {isFemale ? <p>Female</p> : <p>Male</p>}

        {isFemale && <p>Female</p>}

        {isFemale && <p>Male</p>}

        
      </header>
    </div>
  );
}

export default App;
