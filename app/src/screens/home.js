import FAQ from "../components/FAQ";
import AboutUs from "../components/aboutUs";
import ContactUs from "../components/contactUs";
import Footer from "../components/footer";
import Hero from "../components/hero";


function Home() {

    return (
        <div>
            <Hero />
            <div className="w-10/12 m-auto" >
                <AboutUs />
                <ContactUs />
                <FAQ />
            </div>
            <Footer />
        </div>
    )
}

export default Home;