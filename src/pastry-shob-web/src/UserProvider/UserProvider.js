import React, { createContext, useContext } from "react";
import {useLocalState} from "../util/useLocalStorage";

const UserContext = createContext();

const UserProvider = ({ children }) => {
    const [jwt, setJwt] = useLocalState("", "jwt");
    return <UserContext.Provider value={jwt}></UserContext.Provider>


};

export default UserProvider ;