import {useTranslation} from "react-i18next";

const Footer = () => {
    const { t } = useTranslation();
    return (
        <footer>
            <section className="footer">
                <article className="footer-first">
                    <a className="footer-first-title" href="/">{t('footer.footer-first1')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-first2')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-first3')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-first4')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-first5')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-first6')}</a>
                </article>
                <article className="footer-second">
                    <a className="footer-first-title" href="/">{t('footer.footer-second1')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-second2')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-second3')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-second4')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-second5')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-second6')}</a>
                </article>
                <article className="footer-third">
                    <a className="footer-first-title" href="/">{t('footer.footer-third1')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-third2')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-third3')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-third4')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-third5')}</a>
                    <a className="footer-first-item" href="/">{t('footer.footer-third6')}</a>
                </article>
                <article className="footer-four">
                    <a className="footer-first-title" href="/author" target="_blank">{t('footer.footer-four')}</a>
                    <a className="footer-first-item" href="https://github.com/Ivzilol" target="_blank" rel="noreferrer">Github</a>
                    <a className="footer-first-item" href="https://www.linkedin.com/in/ivaylo-alichkov-7406871a1/" target="_blank" rel="noreferrer">Linkedin</a>
                    {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                    <a className="footer-first-item">Email: ivailoali@gmail.com</a>
                </article>
            </section>
            <hr className="footer-line"/>
            <p className="footer-author">© 2023 Ivaylo Alichkov</p>
        </footer>
    )
}
export default Footer;