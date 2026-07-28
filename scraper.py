import requests
from bs4 import BeautifulSoup

URL = "https://xprimehub.bond/"

headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, x509) Chrome/120.0.0.0 Safari/537.36"
}

def fetch_data():
    try:
        response = requests.get(URL, headers=headers)
        if response.status_code == 200:
            soup = BeautifulSoup(response.text, 'html.parser')
            
            # Page Title Extract
            title = soup.title.string if soup.title else "No Title Found"
            print(f"Website Title: {title}\n")
            
            # Page ke Sabhi Links Extract karna
            print("--- Links Found ---")
            for link in soup.find_all('a', href=True):
                print(f"Text: {link.text.strip()} | URL: {link['href']}")
        else:
            print(f"Failed to fetch page. Status Code: {response.status_code}")
    except Exception as e:
        print(f"Error: {e}")

if __name__ == "__main__":
    fetch_data()
