import {useLocalState} from "../../util/useLocalStorage";
import {useEffect} from "react";

const Products = () => {

    const [jwt, setJwt] = useLocalState("", "jwt");


    // useEffect(()=> {
    //     console.log(`JWT is: ${jwt}`)
    // }, [jwt]);

    function createProduct() {
        fetch("api/products", {
            headers: {
                "content-type": "application/json",
                "Authorization": `Bearer ${jwt}`,
            },
            method: "POST",
        }).then(response => {
            if (response.status === 200) return response.json()
        }).then(data => {
            console.log(data);
        })
    }

    return (
        <div style={{margin: "20px"}}>
           <button onClick={() => createProduct()}>Submit new Product</button>
        </div>
    );
}

export default Products;