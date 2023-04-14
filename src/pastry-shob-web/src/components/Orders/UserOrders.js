import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";

const UserOrders = () => {
    const user = useUser();
    let navigate = useNavigate();
    const [products, setProducts] = useState(null);

    useEffect(() => {
        ajax('api/orders', "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });
        if (!user.jwt) navigate('/login')
    }, [user.jwt]);

    return (
        <main className="orders-user">
            {products ? (
                <article className="orders-container">
                    {products.map((product) => (
                        <div className="orders-container-items"
                        key={product.id}
                        >
                            <p className="orders-container-items-name">
                               Product Name: {product.name}
                            </p>
                            <p className="orders-container-items-name">
                                Product Name: {product.price}
                            </p>
                        </div>
                    ))}
                </article>
            ) : (
                <></>
            )}
        </main>
    )
}

export default UserOrders;