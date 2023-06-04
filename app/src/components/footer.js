import { Link } from "react-router-dom";
import facebook from "../icons/facebook.png";
import twitter from "../icons/twitter.png";
import instagram from "../icons/instagram.png";
import home from "../icons/home.png";
import phone from "../icons/telephone.png";
import email from "../icons/mail.png";

function Footer() {
  return (
    <div className="bg-c-blue-100 text-sm">
      <div>
        <div className="flex justify-between bg-c-blue-950 p-4 text-white">
          <h1 className="text-base">social media</h1>
          <ul className="flex gap-8" >
            <LogoLink logo={facebook} name={"facebook"} textColor={'white'} />
            <LogoLink logo={twitter} name={"twitter"} textColor={'white'} />
            <LogoLink logo={instagram} name={"instagram"} textColor={'white'} />
          </ul>
        </div>
        <div className="w-10/12 m-auto grid-cols-3 grid py-12">
          <div className="">
            <h1 className="text-base">site links</h1>
            <ul>
              <CustomLink name={"Home"} />
              <CustomLink name={"About us"} />
              <CustomLink name={"Contact us"} />
              <CustomLink name={"reviews"} />
              <CustomLink name={"frequently asked questions"} />
            </ul>
          </div>
          <div className="">
            <h1 className="text-base">contacts</h1>
            <ul>
              <LogoLink logo={home} name={"some address,address"} />
              <LogoLink logo={phone} name={"07-75-12-18-03"} />
              <LogoLink logo={email} name={"esbitar@gmail.com"} />
            </ul>
          </div>
          <div className="">
            <h1 className="text-base">others</h1>
            <ul>
              <CustomLink name={"diseases & symptomes"} to={'/diseases'} />
              <CustomLink name={"articles"} />
              <CustomLink name={"pharmacy"} />
              <CustomLink name={"medical consultations"} />
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

function CustomLink({ to, name }) {
  return (
    <li>
      <Link to={to} className="underline text-c-blue-950">
        {name}
      </Link>
    </li>
  );
}

function LogoLink({ name, logo, to,textColor }) {

  const _textColor = textColor || "text-c-blue-950";

  return (
    <li className="flex gap-2 mb-2">
      <div className="w-6 aspect-square">
        <img alt="" src={logo} className="w-full aspect-square" />
      </div>
      <Link to={to} className={"underline "+_textColor}>
        {name}
      </Link>
    </li>
  );
}

export default Footer;
