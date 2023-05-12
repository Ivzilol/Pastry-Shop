import {useEffect, useState} from "react";
import ReactLoading from "react-loading";
const Loading = () => {

    const [data, setData] = useState([]);
    const [done, setDone] = useState(undefined);

    useEffect(() => {
        setTimeout(() => {
            fetch("https://jsonplaceholder.typicode.com/posts")
                .then((response) => response.json())
                .then((json) => {
                    console.log(json);
                    setData(json);
                    setDone(true);
                });
        }, 200000);
    }, []);

    return (
        <>
            {!done ? (
                <ReactLoading
                    className="loader"
                    type={"bars"}
                    color={"#03fc4e"}
                    height={100}
                    width={100}
                ></ReactLoading>
            ) : (
                <ul>
                    {data.map((post) => (
                        <li key={post.id}>{post.title}</li>
                    ))}
                </ul>
            )}
        </>
    )
}

export default Loading