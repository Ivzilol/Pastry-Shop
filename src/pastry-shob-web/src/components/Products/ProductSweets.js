import NavBar from "../NavBar/NavBar";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import Footer from "../Footer/Footer";
import baseURL from "../BaseURL/BaseURL";

const ProductSweets = () => {
    const user = useUser()
    const [sweets, setSweets] = useState(null);
    let navigate = useNavigate();
    useEffect(() => {
        ajax(`${baseURL}api/products/sweets`, "GET", user.jwt)
            .then(piesData => {
                setSweets(piesData)
            })
        if (!user.jwt) navigate("/login")
    }, [navigate, user.jwt])


    return (
        <main>
            <NavBar/>
            <section className="pies-container">
                {sweets ? (
                    <article className="products-container">
                        {sweets.map((product) => (
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
                    <>No products here</>
                )}
            </section>
            <Footer/>
        </main>
    )
}
export default ProductSweets;