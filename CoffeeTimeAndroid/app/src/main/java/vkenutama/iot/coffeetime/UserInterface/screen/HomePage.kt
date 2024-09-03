package vkenutama.iot.coffeetime.UserInterface.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import vkenutama.iot.coffeetime.Data.Model.Product
import vkenutama.iot.coffeetime.R
import vkenutama.iot.coffeetime.UserInterface.component.ReusableSpacer
import vkenutama.iot.coffeetime.Util.toDp

@Composable
fun HomePage(navController: NavHostController) {

    val whiteCoffee = Product(
        productName = "White Coffee",
        productImageSourceInt = R.drawable.rectangle_14,
        productPrice = 12000.0
    )

    val espressoCoffee = Product(
        productName = "Espresso Coffee",
        productImageSourceInt = R.drawable.coffee_sample_2,
        productPrice = 25000.0
    )

    val tea = Product(
        productName = "Tea",
        productImageSourceInt = R.drawable.coffee_sample_4,
        productPrice = 8000.0
    )

    val recommendedProductList: List<Product> = listOf(
        whiteCoffee,
        espressoCoffee,
        tea
    )

    //
    val greenTea: Product = Product(
        productName = "Green Tea",
        productPrice = 5000.0,
        productImageSourceInt = R.drawable.coffee_sample_5)

    val hotChocolate: Product = Product(
        productName = "Hot Chocolate",
        productPrice = 12000.0,
        productImageSourceInt = R.drawable.coffee_sample_6)

    val repeatOrderList: List<Product> = listOf(
        greenTea,
        hotChocolate
    )


    Row {
        ReusableSpacer(width = 40.toDp())

        Column (
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ){
            ReusableSpacer(height = 110.toDp())

            Row (
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Start
            ){
                //User Profile Picture
                Image(
                    modifier = Modifier.size(168.toDp()),
                    painter = painterResource(id = R.drawable.user_icon_prev),
                    contentDescription = null,
                    )

                ReusableSpacer(width = 36.toDp())

                Column (
//                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceAround,

                ){
                    Text(text = "Vincent Kenutama P",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold)

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Text(text = "Rp. 10.000,00",
                            fontSize = 14.sp,
                            color = Color(0xFF878484),
                            fontWeight = FontWeight.Bold
                        )
                        ReusableSpacer(width = 25.toDp())
                        Icon(
                            modifier = Modifier.size(35.toDp()),
                            painter = painterResource(id = R.drawable.hide),
                            contentDescription = null)
                    }


                }
            }

            ReusableSpacer(height = 90.toDp())

            //Buy, Menu, Locate, History menus
            Row(
                modifier = Modifier
                    .width(960.toDp())
                    .height(386.toDp())
                    .background(Color(0xFFE4C59E), shape = RoundedCornerShape(28.toDp())),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ){
                MenuItem(icon = R.drawable.shopping_cart, title = "Buy", {})
                MenuItem(icon = R.drawable.coffee_menu, title = "Menu", {})
                MenuItem(icon = R.drawable.location_menu, title = "Locate", {})
                MenuItem(icon = R.drawable.history_menu, title = "History", {})
            }

            ReusableSpacer(height = 51.toDp())

            /*
                Section - Just For You (Recommended Item)
             */
            Text(
                text = "Just for you!",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp)

            ReusableSpacer(height = 44.toDp())


            //Recommended Item Lazy Row
            LazyRow {
                items(recommendedProductList.size){
                    i ->
                    RecommendedItem(product = recommendedProductList[i], {})
                    ReusableSpacer(width = 30.toDp())
                }
            }

            ReusableSpacer(height = 62.toDp())

            /*
                Section - Repeat Order
             */

            Text(text = "Repeat Order!",
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp)

            ReusableSpacer(height = 22.toDp())

            for(item in repeatOrderList){
                RepeatOrderItem(product = item) {
                    
                }
            }










        }



//        ReusableSpacer(width = 40.toDp())
    }

}

@Composable
fun MenuItem(
    icon: Int,
    title: String,
    link: () -> Unit
){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ){
//        ReusableSpacer()
        Box (
            modifier = Modifier
                .background(Color(0xFFAF8260), shape = RoundedCornerShape(28.toDp()))
                .width(160.toDp())
                .height(165.toDp())
                .clickable {
                    link()
                },
            contentAlignment = Alignment.Center
        ){
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(75.toDp()))
        }

        ReusableSpacer(23.toDp())

        Text(
            modifier = Modifier.clickable {
                link()
            },
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp)
    }
}

@Composable
fun RecommendedItem(
    product: Product,
    link: () -> Unit

){
    Column (
        modifier = Modifier.width(IntrinsicSize.Max)
    ){
        Box(
            modifier = Modifier
                .size(450.toDp())
                .background(color = Color.Transparent, shape = RoundedCornerShape(28.toDp()))
        ){
            Image(painter = painterResource(id = product.productImageSourceInt), contentDescription = null)
        }
        ReusableSpacer(height = 5.toDp())

        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Column (
            ){
                Text(
                    text = product.productName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp)

                ReusableSpacer(height = 18.toDp())

                Text(text = "Rp. ${product.productPrice},00",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = Color(0xFF878383))
            }

            Box(
                modifier = Modifier
                    .size(81.toDp())
                    .background(Color(0xFFAF8260), shape = RoundedCornerShape(100))
                    .clickable { link() },
                contentAlignment = Alignment.Center
            ){
                Icon(
                    painter = painterResource(id = R.drawable.add_icon_thin),
                    contentDescription = null,
                    modifier = Modifier.size(35.toDp()))
            }
        }


    }

}

@Composable
fun RepeatOrderItem(
    product: Product,
    link: () -> Unit
){
    Box(
        modifier = Modifier
            .width(960.toDp())
            .height(208.toDp())
            .background(Color(0xFFAF8260), shape = RoundedCornerShape(28.toDp())),
        contentAlignment = Alignment.CenterStart
    ){


        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxHeight()
        ){
            ReusableSpacer(width = 25.toDp())

            Row (
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ){
                Box(modifier = Modifier
                    .size(128.toDp())
                    .background(color = Color.Transparent, shape = RoundedCornerShape(28.toDp()))){
                    Image(
                        painter = painterResource(id = product.productImageSourceInt),
                        contentDescription = null,
                        modifier = Modifier
                            .size(128.toDp())
                            .background(Color.Transparent, RoundedCornerShape(64.toDp())))
                }
                ReusableSpacer(width = 32.toDp())

                Column (
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.height(128.toDp())
                ){
                    Text(
                        text = product.productName,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp)
                    Text(text = "Rp. ${product.productPrice}",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.White)
                }
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ){
                    Box(
                        modifier = Modifier
                            .background(Color.White, RoundedCornerShape(28.toDp()))
                            .size(75.toDp()),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            modifier = Modifier.size(45.toDp()),
                            painter = painterResource(id = R.drawable.add_icon),
                            contentDescription = null)

                    }
                    ReusableSpacer(width = 65.toDp())
                }
            }
        }
    }
    ReusableSpacer(height = 62.toDp())
}

@Preview(showBackground = true)
@Composable
fun HomePagePreview(){
    HomePage(navController = rememberNavController())
}