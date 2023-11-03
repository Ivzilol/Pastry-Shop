import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import Footer from "../Footer/Footer";
import baseURL from "../BaseURL/BaseURL";

const ProductCake = () => {
    const user = useUser()
    const [cakes, setCakes] = useState(null);
    let navigate = useNavigate();
    useEffect(() => {
        ajax(`${baseURL}api/products/cakes`, "GET", user.jwt)
            .then(cakesData => {
                setCakes(cakesData)
            })
        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt])
    return (
        <main>
            <NavBar/>
            <section className="pies-container">
                {cakes ? (
                    <article className="products-container">
                        {cakes.map((product) => (
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

export default ProductCake