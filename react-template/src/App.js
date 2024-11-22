import { CssBaseline, Snackbar, Fade } from "@mui/material";
import { lazy, Suspense, useRef, forwardRef } from "react";
import Loading from "components/Loading";
import { useState, createContext, useEffect } from "react";
import Footer from "components/Footer/Footer";
import { Navigate, Route, Routes } from "react-router-dom";
import WishList from "pages/Wishlist/Wishlist";
import Cart from "pages/Cart/Cart";
import Product from "pages/Product/Product";
import MuiAlert from "@mui/material/Alert"; // Import MuiAlert here

const Home = lazy(() => import("./pages/Home/Home"));
const Navbar = lazy(() => import("./components/Navbar/Navbar"));
export const DataContext = createContext();

const Alert = forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

function App() {
  const [product, setProduct] = useState([]);
  const [logos, setLogos] = useState([]);
  const [wishList, setWishList] = useState([]);
  const [cartList, setCartList] = useState([]);
  const [snackbar, setSnackbar] = useState({
    message: "",
    open: false,
    severity: "",
  });
  const updateWishlist = useRef(false);
  const updateCart = useRef(false);

  // Giả lập lấy dữ liệu sản phẩm (thay thế phần Firebase)
  useEffect(() => {
    // Giả lập dữ liệu sản phẩm và logo
    const fetchedProducts = [
      { id: 1, name: "Sản phẩm A", price: 100 },
      { id: 2, name: "Sản phẩm B", price: 200 },
      // Thêm sản phẩm mẫu khác...
    ];
    const fetchedLogos = ["Logo1", "Logo2"];

    setProduct(fetchedProducts);
    setLogos(fetchedLogos);
  }, []);

  // Cập nhật giỏ hàng khi thay đổi
  useEffect(() => {
    if (updateCart.current) {
      localStorage.setItem("cartList", JSON.stringify(cartList));
    } else {
      updateCart.current = true;
    }
  }, [cartList]);

  // Cập nhật wishlist khi thay đổi
  useEffect(() => {
    if (updateWishlist.current) {
      localStorage.setItem("wishList", JSON.stringify(wishList));
    } else {
      updateWishlist.current = true;
    }
  }, [wishList]);

  // Hàm đóng snackbar
  const handleClose = (event, reason) => {
    if (reason === "clickaway") {
      return;
    }

    setSnackbar({ ...snackbar, open: !snackbar.open });
  };

  // Thêm sản phẩm vào giỏ hàng
  const addToCart = (product) => {
    setCartList((prevCart) => [...prevCart, product]);
    setSnackbar({
      message: "Sản phẩm đã được thêm vào giỏ hàng!",
      open: true,
      severity: "success",
    });
  };

  return (
    <DataContext.Provider
      value={{
        product,
        setProduct,
        logos,
        setLogos,
        wishList,
        setWishList,
        cartList,
        setCartList,
        snackbar,
        setSnackbar,
        addToCart,
      }}
    >
      <CssBaseline />
      <Suspense fallback={<Loading />}>
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/wishlist" element={<WishList />} />
          <Route path="/cart" element={<Cart />} />
          <Route
            path="/product/:productID"
            element={<Product addToCart={addToCart} />}
          />
          <Route path="/product" element={<Navigate to={"/"} />} />
        </Routes>
        <Footer />
        <Snackbar
          open={snackbar.open}
          autoHideDuration={3000}
          onClose={handleClose}
          TransitionComponent={Fade}
        >
          <Alert
            onClose={handleClose}
            severity={snackbar.severity}
            sx={{ width: "100%" }}
          >
            {snackbar.message}
          </Alert>
        </Snackbar>
      </Suspense>
    </DataContext.Provider>
  );
}

export default App;
