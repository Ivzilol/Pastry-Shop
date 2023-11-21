import {Link} from "react-router-dom";

const NotFoundPage = () => {
    return (
        <main className="not-found">
            <div>
                <h1 className="not-found-text">Oops.....</h1>
                <h2 className="not-found-text">There is nothing here!</h2>
                <p className="not-found-text">Go to the <Link className="not-found-link" to="/">Homepage</Link></p>
            </div>
        </main>
    )
}

export default NotFoundPage;