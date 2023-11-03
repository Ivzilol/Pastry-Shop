import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";
import baseURL from "../BaseURL/BaseURL";

const ProductPies = () => {
    const user = useUser()
    const [pies, setPies] = useState(null);
    let navigate = useNavigate();
    useEffect(() => {
        ajax(`${baseURL}api/products/pies`, "GET", user.jwt)
            .then(piesData => {
                setPies(piesData)
            })
        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt])


    return (
        <main className="products-users">
            <NavBar/>
            <section className="pies-container">
                {pies ? (
                    <article className="products-container">
                        {pies.map((product) => (
                            <div
                                className="products-container-items"
                                key={product.id}
                                id={product.id}
                            >
                                <p className="products-container-item"
                                >Продукт: {product.name}</p>
                                <p className="products-container-item"
                                >Цена: {product.price} лв.</p>
                                <p className="products-container-item"
                                >Описание на продукта: {product.description}</p>
                                <img
                                    className="product-img" src={product.imageUrl} alt="new"
                                />
                            </div>
                        ))}
                    </article>
                ) : (
                    <></>
                )}
            </section>
            <Footer/>
        </main>
    )
}

export default ProductPies;