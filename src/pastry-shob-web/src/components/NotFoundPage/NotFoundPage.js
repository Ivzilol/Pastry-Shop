import {Link} from "react-router-dom";

const NotFoundPage = () => {
    return (
        <main className="not-found">
            <div>
                <h1>Oops.....</h1>
                <h2>Page not found!</h2>
                <p>Go to the <Link className="not-found-link" to="/">Homepage</Link></p>
            </div>
        </main>
    )
}

export default NotFoundPage;