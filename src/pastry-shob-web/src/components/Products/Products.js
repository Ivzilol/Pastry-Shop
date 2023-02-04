import {useLocalState} from "../../util/useLocalStorage";
import {useEffect} from "react";

const Products = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");

    useEffect(() => {
        if (!jwt) {
            const requestBody = {
                "username": "Ivzilol",
                "password": "asdasd",
            };

            fetch("api/auth/login", {
                headers: {
                    "Content-Type": "application/json"
                },
                method: "post",
                body: JSON.stringify(requestBody)
            })
                .then((response) => Promise.all([response.json(), response.headers]))
                .then(([body, headers]) => {
                    setJwt(headers.get("authorization"));
                });
        }
    }, [jwt]);

    useEffect(()=> {
        console.log(`JWT is: ${jwt}`)
    }, [jwt]);

    return (
        <div>
            <h1>pastry-Shop</h1>
            <div>Jwt value is: ${jwt}</div>
        </div>
    );
}

export default Products;