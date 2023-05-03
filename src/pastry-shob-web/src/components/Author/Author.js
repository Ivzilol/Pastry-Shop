import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";

const Author = () => {
    return (
        <main className="author">
            <NavBar/>
            <section className="author-container">
                <div className="author-container-items">
                    <div className="author-container-img">
                        <img src="https://i.imgupx.com/BJeqpcTC/ivo.jpg" alt="new"/>
                    </div>
                    <div className="author-container-text">
                        <h2>Ивайло Иванов Аличков</h2>
                        <p>Описание.......</p>
                    </div>
                </div>
            </section>
            <Footer/>
        </main>
    )
}
export default Author