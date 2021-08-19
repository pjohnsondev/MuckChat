package muck.client;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

class SocialMediaShareTest {

    @Test
    void getImageBase64String() throws IOException {
        // Test string for test.jpg encoded to Base64 by Cyberchef.com
        String testImageOne = "iVBORw0KGgoAAAANSUhEUgAAAOAAAADZCAYAAAA5WDyEAAAAAXNSR0IArs4c6QAAAARnQU1BAAC" +
                "xjwv8YQUAAAAJcEhZcwAAHYcAAB2HAY/l8WUAAAAhdEVYdENyZWF0aW9uIFRpbWUAMjAyMTowODowNyAxMDowNDo0" +
                "MSMLEVsAABh1SURBVHhe7Z1fiNvXlcePNLbjduwkddJumtDSNtKwuKZ58G4J0ltMaEemIbQwFJoQ+lCJ5iEzL8b74" +
                "N1mYdit66VoWgiMQjdM3abB0GKSWtoQmoeW0UMghcDEDZYa8tLQuIUmnaQz45nRb885917ppz8jz9i6uon0/cwc3b" +
                "+/309j66tz77k/6SYihgAAQUjaFAAQAAgQgIBcdwj69vs2AwAYOPCAAAQEAgQgIBAgAAGBAAEICAQIQEAgQAACAgE" +
                "CEBAIEICAeFmI/6e/L9HqO7/nk29TIpmgRCKplkxy2mYTsbYJfjtIUpScpHdun7NnAmC08eIBJw4colvvzlDywGFS" +
                "favGI/PjynGzbZJPNtb0HACMA56GoCykfQfp0F33076Dd1h5xcTWFF1DUzVbZwyA8cCbACna4KFlRJOfztL+yc+qw" +
                "OLeL2rYVMvmmJa3BGA88ChANhlObq/Txz55nG458iWtlx9td300z56wmRcDYDzwJ0AnpMYHLML36MDhzzc9XM9U+k" +
                "v+Qy/AOj19IkH3HC7Qy7YGgBvFjwDbNMSFxjVOtlVoTmxNoWlZEneQS31TodMqpCw9/aatAmDIePSAFhWWDDHtMJP" +
                "NeD7TZvJaMKZ5AMYDzwKU1M3v2APKjxWb/Jh2Nq6TnCkPi2k6+5uI/rS6TN/5gq0CYMh4EqAgwrLLDOr9xPS3PQLK" +
                "NU0vaMsAjAt+BaiKExGK+Mwc0IhMEpfqo2ZM1tQDMA74E2DT6xlxiQhFdGpOcFofF1w875sKnT7cJ5r5ZoWefiLL7" +
                "dLH2ENPVOgt27wzdXr5qQI9FDvunhNZOv1S3bZ3YvtrQMgZ939qN9cCH3U8CVC8nhOX84C2TkRnfk1eadXbh7C8uU" +
                "AP3ZejJ5+pEn05Q498O8+WoVefyVH2xDmWzE5IZDVNj54u0avUOo5eqdLPvp6mh57qPFKWNLr7Hyfuf5qvxYIHo40" +
                "fAaqY2r2fGYJyos3yaOG8KUqjyd+yGfK9n0V03xwLguiRX9XoT79ZprM/WmRbpj+t1uhJKtHPXjE92xEx5bTt+Fk+" +
                "brXjuC8TvXr6se4ljy/m6fxrUdt1nrf96Zl5LJGMOJ48oMqMLeb1VISu3taJ4Lhk6hwR3fb352j/1p9tebi89dQ8/" +
                "Uwy3y7T2QdTWtciRd95usgeqgcvnaMnRZh83POP9zju3/KcVunJYtyrcT2L7oGuKGyKHvwGe07uX/+jqQGjiScPKG" +
                "JzghMRxsoiOkn0R/oa8cmj84zJaJ2OrJ7X/LB58w0edvJw8Mm5aVPRyRdO0tfEO3Xw8gslfuxz3IMP0yOSvn6lx9y" +
                "O54EvLfCcs8BD2KzOB7On5XmAUcffEFQl5UQnIpR1QFOrjyYTo71CRDh86vTH1yU9RvfuaW3QHcce7r54MCVuPDzV" +
                "vnFYeBro4Xng1+d4zlmiP0g1D0sf6SFyMHr4C8J0irDRMBKzOjMJP0pGRaoVFq002Y8YxzWQ0se+MWV7ynD3MXpUA" +
                "z1FMw9cjeh5ngs+z8PS7+oQFIw6HueArSGn5tkDar2WhVbqckqXGEOwQn/cMfhRo3pXECZF935R0gx9bU4CKX3s8W" +
                "n6nB5Tp5d+KcPMPJ3/zWzXPNAMhcGo43EOKCqSlC0xQdH2Zpe2JN+utVipvWFIOCFV6YX/22Gx4aWLPYaSPDX8ZxM" +
                "02fG4LnoJ2eGGtGDU8e8BtRjRu1ffMHmmuQwxJJG99ZRZUO9eh+vmgTkT5ey5ZPBmhU5/XYIt3Xzu8TMaZHn1dHqH" +
                "RXeZ7xVi50xTSud5JXrxJa2wSL/HTEQVjDz+gjBqMvQ06cbaqmljEja9HltbWy2x3jBuqMfi+OWl699d8oVZ+tFZ4" +
                "800oCJ3sWh0kvP35egPZ8tmja6LaTr7mhGvLLrL3SwPyXFs5i6XNM/3VkxXxS01SH9+czhh+2q/Y/SkPgcw6vgLwq" +
                "h7cyIU2zsfvL9K2zctwtYL/fg3Ttr5V38+9/gyLf+qaCKRchfLMyUedmZ0Yf75x9OmUy9YvM+/xgK1d7O8Ksexvfo" +
                "Kl78tgZb2T16Y6+SNx33F9CXut7y6SA+aLmDE8fK1hLf/42X6+DWZxMgwVIIvSXr7E/iqQQA68eIB3/34A2wnrPcT" +
                "fd+YBwRg1PE0BCX6x4Gj9Jdbv8XS269CXF9bo61NiYTezHASgNHCmwCFzYlP0dXbC7S+7166dm1D6xKJ3YZgABh9s" +
                "Ec8AAHx6gEBAP2BAAEICAQIQEAgQAACAgECEBAIEICAQIAABOS664AAAH/AAwIQEAgQgIBAgAAEBAIEICAQIAABgQ" +
                "ABCIiXZYjtt5do9Z3f88m3KZFMUCKRVEsmOW2ziVjbBL8dcH5ikhL34OsrwHjgxQNOHDhEt96doeSBw+YT8Kpxsxt" +
                "Esxw326b57TU9BwDjgKchaETJfQfp0F33076Dd1h5xcTWFJ37zphWmzEAxgNvAqRog4eWEU1+Okv7Jz+rAot7v7Z9" +
                "4vlXHlreEoDxwKMA2Ro8nNxep4998jjdcuRLWi8/2u76aF6+vjBWD8CY4E+ATkiND1iE79GBw59verieqfSXPAQIx" +
                "gg/AmzTEBca1ziRLapbYmsKTcuSuINcCsDo49EDWlRYMsS0w0w24/lMm8lrwZjmR4B6hRYKWcou7Ha3JDCOeBagpG" +
                "5+JzvkWsGZnG1n4zrJmfJoUL80T3Ml7PEH+uNJgIIIyy4zqPcT09/2CCjXNL2gLQMwLvgVoCpORCjiM3NAIzJJXKq" +
                "PmjFZUw/AOOBPgE2vZ8QlIhTRqTnBaX1ccPG8D+pUWShQNiu3xznjeVphgSo999RcoKz0KVRsRScVKug5Cpwz1Bey" +
                "et70nBl+VufSzWthPgg68SRA8XpOXM4D2joRnfk1eaVVbx8Gj4opTbm5ElWrGcrn82oZqlK1NEe5dJZ21NlemJox5" +
                "3b7a2bMdcRmpmwdABY/AlQxtXs/MwTlRJvl0cJ5U5RGm1+/7j62e4Q9VXqOpUaUL9f4Gsu0uLiotswXrJXz3FKlUq" +
                "7lyW6U1PSsnvfUjFFgZuZU81qz0ymtA8DhyQOqzNhiXk9F6OptHZuUTJ0josbV54iu/dmWb576wjzpzu75Mi32EEF" +
                "qepFqRRFMieYxTARDxJMHFLE5wYkIY2URnST6I32N+OSx6Rkb6xRdPW/yA6B2WXxfhoqnpk1FD1InZ7gH+8ELl3im" +
                "CMBw8DcEVUk50YkIZR3Q1OqjycToqGARDoY6XVmR9BhN9RsBpqa4BwDDxV8QplOEjYaRmNWZSfhRMipSrbBopckCM" +
                "MJ4nAO2hpyaZw+o9VoWWqnLKV1iHBQrdKXf2LJ+hXswx6YIoRIwLDzOAUVFkrIlJijalv3h27Ul+XatxUrtDTdBik" +
                "5qRLJKc+d2jnHWL13QKGnmaNpUxCld7B0drVw0wR0AbhD/HlCLEb179Q2TZ5rBloGJrD+p2TMkCw1UyvVcDK9XCnb" +
                "hPE9nZmP+L3WSzGpCj+hovUKF3PXlV71cszkAuvEXhFGToadJN9ZWTRuTsOn12Nraaon1ppimxVrRRDn1zhS5+6VA" +
                "BTa50yWtQspQsbbIPeOkaPaMStccl83qMQVOE+kcrRSLRtg9cFFVFX1WrsXHYokDdOAvCKPuzYlQbO+srq4OToSpW" +
                "Vqu1aicz7Aw5O6XEpXYqpkM5YtlqkXLFHd+TWSNsMxCU/VW9RiRqyzoL8/2ubVFrleWO23kMLkWe1jcCQM68LM70t" +
                "9eJvrgdc7IMFSCL6zzz+CrBgHoxI8H/MQDbCes9xN935gHBGDU8TQEZSaPEt31Lfax+1WIa2trtLkpkdDBO1wAPqr" +
                "4E6Cw/1NEdxeIDt5LGxsbWiUfywEAGLBDLgAB8esBAQB9gQABCAgECEBAIEAAAgIBAhAQCBCAgECAAAQEAgQgIBAg" +
                "AAGBAAEICAQIQEAgQAACAgECEBAIEICAePk40vbbS7T6zu/55NuUSMrWXEm1ZJLTNpuItU3w2wHnJyYpcQ++vgKMB" +
                "1484MSBQ3Tr3RlKHjhsPgGvGje7QTTLcbNtmt9e03MAMA54GoJGlNx3kA7ddT/tO3iHlVdMbE3Rue+MabUZA2A88C" +
                "ZAijZ4aBnR5KeztH/ysyqwuPdr2yeef+Wh5S0BGA88CpCtwcPJ7XX62CeP0y1HvqT18qPtro/m5esLY/UAjAn+BOi" +
                "E1PiARfgeHTj8+aaH65lKf8lDgGCM8CPANg1xoXGNE9miuiW2ptC0LIk7yKUAjD4ePaBFhSVDTDvMZDOez7SZvBaM" +
                "aR6A8cCzACV18zvZIdcKzuRsOxvXSc6UARgfPAlQEGHZZQb1fmL62x4B5ZqmF7RlAMYFvwJUxYkIRXxmDmhEJolL9" +
                "VEzJmvqARgH/Amw6fWMuESEIjo1Jzitjwsunh80daoUsrofoHw9vlo2SwvxrW/rC6a9IJWmv+lb6Nght66bemazsX" +
                "PpnoML3ALA7vEkQPF6TlzOA9o6EZ35NXmlVW8fBkyFCok05UpVqmbylM+zZTK639+FHTaOrxRM/274XNm0burJh1N" +
                "GzsVm9hy8QJegQLAH/AhQxdTu/cwQlBNtlkcL501RGm1+/S2pGBj1hXndVDNTrFG0vEiLi2zLy3ytGp05afq0sTJP" +
                "8yXZMdd46ShyO+fWaSGbI9FlJi+beka0LOdiW+Z8rTyDPTjBnvDkAVVmbDGvpyJ09baOTUqmzhFR4+pzRNf+bMs3T" +
                "+2y8WTHpjq3wE3RdKrHtrjs2o6Ve+yYWzlHZiv5Mgtvmo9uJzU9y+ezBQB2gScPKGJzghMRxsoiOkn0R/oa8clj0z" +
                "M21im6et7kB0D6qO7WTqV5nsvtZoiYKdKp9s3ilcpF8aOsv4d7NAJwA/gbgqqknOhEhLIOaGr10WRidFSwCAdFanZ" +
                "J94bnSRrl0gkNvhR2pcQ4dbqyImmeoD8wKPwFYTpF2GgYiVmdmYQfJaMi1QqLVprsQOCh5iLP+WrlZvCllEuzEHfw" +
                "iMemuoaXAPjA4xywNeTUPHtArdey0EpdTukS4wBJTTeDL02P+Nhelw5WaIfAKQB7xuMcUFQkKVtigqJt2R++XVuSb" +
                "9darNTeMGDEIy5RUaaG1ctUM5XXIUUnZ/QAmjvXvioIwI3i3wNqMaJ3r75h8kwz2OJVZI46VSqVHl6uRjY4umtSs2" +
                "d4BsiUcpRd6D5jvbJAPaoB2BF/QRg1GXqadGNt1bQxCZtej62trZZYb4Ir8zlKJxKU5TlfoSAmd7jkdG0wX3ZrfLu" +
                "Bh7C1IqkfnOM5pN79Ys4pd9CkcxdMNwB2ib8gjLo3J0KxvbO6ujoAEfLQ8UyeTOylRKWSWJXLeSrXarS414hmapaW" +
                "+TiZQ5q7X8w5q3yBfHmJTiJ6A/aAl68lpL+9TPTB65yRYagEX1jnn8FXDQLQiR8P+IkH2E5Y7yf6vjEPCMCo42kIy" +
                "kweJbrrW+xj96sQ19bWaHNTIqGDd7gAfFTxJ0Bh/6eI7i4QHbyXNjY2tEo+ugMAMPiZAwIAdoVfDwgA6AsECEBAIE" +
                "AAAgIBAhAQCBCAgECAAAQEAgQgIBAgAAGBAAEICAQIQEAgQAACAgECEBAvN2M/++yzmsqpWyafDJSH7jpXNs0RTR6" +
                "apK985St05x136HkAGFW8CfCb3/ymLbVou1DXVSNKJpP03HPPUSaTod/+7nc0/dWv0p133mnbARg9vAxBnaYbjQZt" +
                "i20ba2xvcxqzxjZtcepM+gtHjhyhbCZLl8pl+utf/6p1AIwiXgXYSau2/4dyRZy33XYr3X///fTCry/RXyBCMKJ4F" +
                "WBvGTKufYcO8k1oIsLDhw7Rv/7Lcfr1Cy/YFgBGC08CtBmhmd9Jjt31S0tLdP78efrFL35BL774Iv39/fdtCwCjhZ" +
                "cgzNJPf0qPPvIIe7HWt6FJtNPAw0+5pE1MXoaknPLvu++9GxNwRPsm9tHPn/05zT7xhK0DYHTwsw4Y07QuM4iyFKe" +
                "6WBcnPsttt91Ot99uzeZjpwNjgWwp3mtv/tHD2xxQNNMSnhATnz4ycWXFuyqtiptx0vUF+Rp6+c/cjWUD7+0g+89/" +
                "GJ5HLz7Mz+2ji+coqHg3ER4nrk6qFC7zi940Glo5gyvf3Cj5mK4rtpltIc61tx2z9R6pV2ihkO25uQsYQ/jFPXB+8" +
                "pP/jba2tqONjWstu3YtWudUbGNjo5lfl/y6sbWmrUdra+vRP9g2N7ei//nhD+2ZB0OtmBFFR/myrRgi7tqZYs3WgG" +
                "7KUV7ff/OcG238eED5t3OersMBSiZqNnKlrXfNjniZn6fNATBa+BuCmt824ak1h51S1haXWFr1TTrLAIwIHueA1pw" +
                "CRXjxOV97YpH+nXVcbqp4mNSpsmD2/WsGabJZKvTcVF7g/jK36+i/YMN4LhiUnjO7gpr9BU2/1nxwp+hfe71sBGoC" +
                "ItayBb5OnzmlnXc2++u+hrJpaZ0W9Dy7iTZe57llzVbf9Ur834z/veJzXX4eXc+7z9MeB7wI0ChIxBYXnbOupAVXd" +
                "NVxzfAFKC+UNOXmSlSlDOXzeTbdYJBKuXSPAIq8CLl/qUrVjPRly5j+F9yG8lMztt4UyfVjm5mydbvgCgs5nZujFX" +
                "LX4cpqieb4ebGmuqkvUDadozl+bsTPyVxTDslROnuOLttug6Cmz22Fp25yDX1iVJI3GnliIkx+HqXO550e86gqv7g" +
                "HzuJiKdra2jIBFmc20NIebOkOunTatc3N6Ps/+IE982DoH4SpRcWMvg/0CJS4tkwUb9o5sFKLyrX2uv5BmJ2CD66e" +
                "LdN+bcGdkzJFvmKc1nH5cvdzc3/n7oIdu3luHdevFSPWmb1G99/cfN5d/xE7XWv08BeEMZmWtWdjcI357cZ6vqE6w" +
                "Mo50lFivkzLs53b3aZo9gy/NPidfe5cy93U7Gbzx6a6+0+nBrtlbv7MMnU+rebe9dULdCk+4luY12245W9ZnO7xty" +
                "yZ7bYHQ4aKS7N81hipWdJ/LiFTpKWOJ546OWOuX7rYMawdH7zNAUUzndYN15rfbuw5TLb30T6oXJSXLL+YTu2wd/X" +
                "0w+bFvnJF5zxC+qh5GZfmeX7kdTiVp4d7Pq1peti90GOYN4Y+f0vqJM0MTIHHqOv9h3H/NpmZk+3iFFJTfNR44zEI" +
                "0w9RnZj+dhMTnzI0AdbpCk9h1MOlY8GCNpN5TDup2SXdM17mNDk5rm+wZli4v6W3MAZO5iilbXb3pMnqc2zxJECba" +
                "SIV1mLCE+uiU3zMMD2gI6OBhD7WFjnhoebiMkW1cjP4IsEaifIF1yH4UOMpCuoUJqnLt2V74oaunfSu9UGKpnRMlK" +
                "GZU4u0uNjHZqd7DKmmaXGZhRjVWh7xMROeD8cKuUBsNzWy01cQCK9BGCc2ZzsiwhN17kCfpoFj5ixVuhCPZuwZ8Yh" +
                "LVNRTXeaXeQjcm0mfv6VysWs4DYZLoDmgxQrver2HOQR1EUVZKO89j5MF9/gCMpcrsqjdSX/vUr3sX5bTp0yUszr3" +
                "WPdamyyK53rLz900gBvG/TOkOWAcbtyl8BzDFKBEFBdr5oWr87iE3DVSoAJbVu/ikAV3jW40uTKfo7S8YHnOJ/0Ke" +
                "teJCdbky4t8xhat0HvO9u+4W2SQpGZpybhhE1SS4JA8P/k70jlaKZaNl26jTpcumHeO6oVLgYfPo49HD2hN8k5waq" +
                "ZlT8hBw4RfuMs1fnHyPC7DL95qqUQltmqVy/kilWvxtbgUnTyTl5tMuN30K5WqXM5zvxotdq4AyLnL3J+zpj+/2Pd" +
                "wJ8xeSc0uU61ctHeeVM3z46vnyzVanu0Vt+S/x65N9Fw6AAPFy1dSLPz4x/T4d79Lm5tbtubG2b9/H/3Xf3+fvvfv" +
                "Z2wNGBxyC525PawctXtqMBz8RUEZEc/NmuDhPQII9Sukg+kbWsMDg8CLBywu/MgON2WeZ1IzGo3XWWH1qGvW87lc+" +
                "T+/9x/m5GBgSLBFP52RL1PUNVYGw8CLAMGHBfkY0hWaOjVLnbeCyseG0hoFzVCxbU4LhgkEONK4OR4jH0U6JguDK7" +
                "QiH5uSOg3GLHcHisDQgABHHPnw7rn5C7RSdaITJJo7Q2d6eEYwXCBAAALiJwoKANgVECAAAYEAAQgIBAhAQCBAAAI" +
                "CAQIQEAgQgIBAgAAEBAIEICAQIAABgQABCAgECEBAIEAAAgIBAhAQCBCAgECAAAQEAgQgIBAgAAGBAAEICAQIQEAg" +
                "QAACAgECEBAIEICAQIAABAQCBCAgECAAAYEAAQgIBAhAQCBAAAICAQIQEAgQgIBAgAAEBAIEICAQIAABgQABCAgEC" +
                "EBAIEAAAgIBAhAQCBCAgECAAAQEAgQgIBAgAAGBAAEICAQIQEAgQAACAgECEBAIEICAQIAABAQCBCAYRP8P4JwmlO" +
                "1vplsAAAAASUVORK5CYII=";

        String compareImageOne = SocialMediaShare.getImageBase64String("./src/test/test-images/test.png");

        assertTrue(testImageOne.equals(compareImageOne));


    }

    @Test
    void formatUri() throws IOException, URISyntaxException {
        assertThrows(MalformedURLException.class, () ->{
            SocialMediaShare.formatUri("test");
        });

        assertDoesNotThrow(() -> {
            SocialMediaShare.formatUri("http://google.com");
        });

    }

    @Test
    void getFBShareUrlFromID() {
        String testID = "uPoGur";
        String testUrl = "https://www.facebook.com/sharer/sharer.php?kid_directed_site=0&sdk=" +
                "joey&u=https://i.imgur.com/uPoGur.jpeg&display=popup&ref=plugin&src=share_bu" +
                "tton";

        String compareUrl = SocialMediaShare.getFBShareUrlFromID(testID);

        assertTrue(testUrl.equals(compareUrl));

    }

    @Test
    void getImgurIdFromResponse() throws IOException {
        String testResponse = "{\"data\":{\"id\":\"lYiszY0\",\"title\":null,\"description\":null,\"datetime\":16" +
                "28300681,\"type\":\"image\\/png\",\"animated\":false,\"width\":224,\"height\":217,\"size\":6413" +
                ",\"views\":0,\"bandwidth\":0,\"vote\":null,\"favorite\":false,\"nsfw\":null,\"section\":null,\"" +
                "account_url\":null,\"account_id\":0,\"is_ad\":false,\"in_most_viral\":false,\"has_sound\":false" +
                ",\"tags\":[],\"ad_type\":0,\"ad_url\":\"\",\"edited\":\"0\",\"in_gallery\":false,\"deletehash\"" +
                ":\"TJJcXX5VsYG2wyM\",\"name\":\"\",\"link\":\"https:\\/\\/i.imgur.com\\/lYiszY0.png\"},\"succes" +
                "s\":true,\"status\":200}\n";
        String testID = SocialMediaShare.getImgurIdFromResponse(testResponse);
        String compareID = "lYiszY0";

    }

}