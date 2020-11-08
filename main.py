import re
import os
import urllib.request
import requests
import json
import time
import random
from bs4 import BeautifulSoup


# 创建目录
def mkdir(path):
 
	folder = os.path.exists(path)
 
	if not folder:                   #判断是否存在文件夹如果不存在则创建为文件夹
		os.makedirs(path)            #makedirs 创建文件时如果路径不存在会创建这个路径
		print("---  new folder...  ---")
		print("---  OK  ---")
 
	else:
		print("---  There is this folder!  ---")

# _ud.mp3:超高清; _hd.mp3:高清; _sd.m4a:低清

def get_music_lizhifm(sheetid):
    
    url = 'https://m.lizhi.fm/vodapi/playsheet/data?id={}&page=1&count=500'.format(sheetid)
    headers = {
      'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36',
    }
    html = requests.get(url, headers=headers).json()    
    
    if html['voices']:
      sheetinfo = html["playSheetInfo"];
      downlaodfolder = './download/{}-{}/'.format(sheetinfo["name"],str(sheetinfo["voiceNum"]));
      mkdir(downlaodfolder)
      out = open(downlaodfolder +'info.json','w')
      out.write(str(html))
      out.close()      
      voices = html['voices']
      print("get {} in {}".format(str(len(voices)),str(sheetinfo["voiceNum"])))
      for voice in voices:
        print(voice["name"])
        print(voice["cover"])
        print(voice["voiceTrack"])
        name=voice["name"]
        cover_url = voice["cover"]
        mp3_url = voice["voiceTrack"]
        if(os.path.isfile(downlaodfolder+name+'.mp3') is False):
          urllib.request.urlretrieve(mp3_url,   downlaodfolder+name+'.mp3')
          urllib.request.urlretrieve(cover_url, downlaodfolder+name+'.jpg')
        #加个延时
        #time.sleep(random.randint(1,10))
      return mp3_url
    else:
      #print("!!!"+html['msg'])
      return None

# https://m.lizhi.fm/vod/user/2540285708286522412    

def downloadFromPage(startUrl):
  page = requests.get(startUrl)
  sheetId = re.findall('([0-9]{19})',startUrl)[0]
  downloadurl = get_music_lizhifm(sheetId)
  urlList = []
  bs = BeautifulSoup(page.content, features='lxml')
  if downloadurl:
    title = bs.select(".audioName")[0].text
    print(title)
    urllib.request.urlretrieve(downloadurl, './download/'+title+'.mp3')
  # get next url
  for link in bs.findAll('a'):
      url = link.get('href')
      downloadableUrl = re.findall('(^[0-9]{19}$)', url)
      if downloadableUrl:
        urlList.append(downloadableUrl[0])
  if(len(urlList) == 2):
    nextUrl = 'https://www.lizhi.fm'+userId+urlList[1]
    print('nextUrl: ' + nextUrl)
    downloadFromPage(nextUrl)
  else:
    print('urlList length error:'+ urlList)
    exit()


if __name__ == '__main__':
    print('*' * 30 + 'ready to download' + '*' * 30)
    mkdir("./download/")
    voclist = [
    "2540285708286522412",
    "5137432413271024705",
    "5135496534092520513",
    "5133917601013986881",
    "5132198192444757057",
    "5130597898470046785",
    "5120955797745517121",
    "5119828875606177857",
    "2667635973481609793",
    "2617728042945074241",
    "2692636203113692225",
    "5086457317086023873",
    "5086457183942037185",
    "5086457087305272001",
    "5078633175909218497",
    "2693235630227395649"]
    for vocid in voclist:
      print(vocid)
      get_music_lizhifm(vocid)
