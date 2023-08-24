import NavBar from "../NavBar/NavBar";
import Footer from "../Footer/Footer";
import {useTranslation} from "react-i18next";

const Author = () => {
    const {t} = useTranslation();
    return (
        <main className="author">
            <NavBar/>
            <section className="author-container">
                <div className="author-container-items">
                    <div className="author-container-img">
                        <img src="https://i.postimg.cc/nrn81M2p/ivo.jpg" alt="new"/>
                    </div>
                    <div className="author-container-text">
                        <h2 data-testid="Author">{t('author.name')}</h2>
                        <p><p>{t('author.p1')}</p>
                            <p className="author-firstP">{t('author.p2')} <a href="https://github.com/Ivzilol/Pastry-Shop" target="_blank">{t('here')}</a> {t('author.p3')} </p>
                            <p>{t('author.p4')}</p>
                            <p>{t('author.p5')}</p>
                        </p>
                    </div>
                </div>
            </section>
            <Footer/>
        </main>
    )
}
export default Author