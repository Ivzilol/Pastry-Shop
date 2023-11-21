import {Link} from "react-router-dom";

const NotFoundPage = () => {
    return (
        <div>
            <h1>Oops.....</h1>
            <h2>Page not found!</h2>
            <p>Go to the <Link to="/">Homepage</Link></p>
        </div>
    )
}

export default NotFoundPage;