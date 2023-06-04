import image from "../assets/hero-2.jpg";

function AboutUs() {
  return (
    <div className="mb-4" >
      <h1 className="text-4xl font-bold text-c-blue-950 m-2">About us</h1>
      <div className="flex gap-3 r-sm:block">
        <div className="w-5/12">
          <img src={image} alt="" className="w-full" />
        </div>
        <p className="w-7/12">
          <div className="w-[50ch] m-auto" >
            At <span className="font-semibold text-c-blue-600">e-Sbitar</span>,
            we are passionate about transforming healthcare. Our goal is to make
            quality healthcare accessible to everyone, anytime, and anywhere.
            Through innovative technology and a dedicated team of professionals,
            we connect patients with healthcare providers in a convenient and
            personalized way. Whether you need medical advice, virtual
            consultations, or reliable health resources, our platform delivers
            seamless and secure experiences. Join us as we leverage technology
            to empower individuals and improve the health and wellness of
            communities worldwide.
          </div>
        </p>
      </div>
    </div>
  );
}

export default AboutUs;
