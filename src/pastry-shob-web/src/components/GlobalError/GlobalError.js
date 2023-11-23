import NavBar from "../NavBar/NavBar";

const GlobalError = () => {

    return (
        <main className="error-global">
            <NavBar/>
            <div className="global-error">
                <h1>Oops! Something went wrong.</h1>
                <p>We now for the problem and we are working ot its removal!
                    Please try again later.</p>
            </div>
        </main>
    );

}

export default GlobalError;