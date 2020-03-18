import webcolors
import pandas as pd
import numpy as np
import cv2
from sklearn.cluster import KMeans
from collections import Counter
import operator

import webcolors
import pandas as pd
import numpy as np
import cv2
from sklearn.cluster import KMeans
from collections import Counter
import operator

def colouDict():
    color_dict = {}
    for key, value in webcolors.CSS3_HEX_TO_NAMES.items():
        
        color_dict[key] = value

        color_dict['#dcdcdc'] = 'white'
        color_dict['#f8f8ff'] = 'white'
        color_dict['#ffd700'] = 'yellow'
        color_dict['#daa520'] = 'gold'
        color_dict['#adff2f'] = 'green'
        color_dict['#f0fff0'] = 'white'
        color_dict['#ff69b4'] = 'pink'
        color_dict['#cd5c5c'] = 'red'
        color_dict['#4b0082'] = 'purple'
        color_dict['#fffff0'] = 'white'
        color_dict['#f0e68c'] = 'gold'
        color_dict['#e6e6fa'] = 'white'
        color_dict['#fff0f5'] = 'pink'
        color_dict['#7cfc00'] = 'green'
        color_dict['#e0ffff'] = 'cyan'
        color_dict['#f08080'] = 'pink'
        color_dict['#fafad2'] = 'yellow'
        color_dict['#d3d3d3'] = 'white'
        color_dict['#ffb6c1'] = 'pink'
        color_dict['#ffa07a'] = 'orange'
        color_dict['#20b2aa'] = 'green'
        color_dict['#87cefa'] = 'lightblue'
        color_dict['#778899'] = 'gray'
        color_dict['#b0c4de'] = 'blue'
        color_dict['#ffffe0'] = 'yellow'
        color_dict['#00ff00'] = 'green'
        color_dict['#32cd32'] = 'green'
        color_dict['#faf0e6'] = 'beige'
        color_dict['#800000'] = 'brown'
        color_dict['#66cdaa'] = 'green'
        color_dict['#0000cd'] = 'blue'
        color_dict['#ba55d3'] = 'purple'
        color_dict['#9370db'] = 'purple'
        color_dict['#3cb371'] = 'green'
        color_dict['#7b68ee'] = 'blue'
        color_dict['#00fa9a'] = 'green'
        color_dict['#48d1cc'] = 'green'
        color_dict['#c71585'] = 'red'
        color_dict['#191970'] = 'dark blue'
        color_dict['#ffe4e1'] = 'rose'
        color_dict['#ffe4b5'] = 'gold'
        color_dict['#ffdead'] = 'gold'
        color_dict['#000080'] = 'dark blue'
        color_dict['#fdf5e6'] = 'white'
        color_dict['#808000'] = 'green'
        color_dict['#6b8e23'] = 'green'
        color_dict['#ff4500'] = 'orange'
        color_dict['#da70d6'] = 'purple'
        color_dict['#eee8aa'] = 'gold'
        color_dict['#98fb98'] = 'green'
        color_dict['#afeeee'] = 'cyan'
        color_dict['#db7093'] = 'pink'
        color_dict['#ffefd5'] = 'beige'
        color_dict['#ffdab9'] = 'gold'
        color_dict['#cd853f'] = 'orange'
        color_dict['#dda0dd'] = 'violet'
        color_dict['#b0e0e6'] = 'cyan'
        color_dict['#bc8f8f'] = 'brown'
        color_dict['#4169e1'] = 'blue'
        color_dict['#8b4513'] = 'brown'
        color_dict['#fa8072'] = 'orange'
        color_dict['#f4a460'] = 'orange'
        color_dict['#2e8b57'] = 'green'
        color_dict['#fff5ee'] = 'green'
        color_dict['#a0522d'] = 'brown'
        color_dict['#87ceeb'] = 'blue'
        color_dict['#6a5acd'] = 'violet'
        color_dict['#708090'] = 'gray'
        color_dict['#fffafa'] = 'white'
        color_dict['#00ff7f'] = 'green'
        color_dict['#4682b4'] = 'blue'
        color_dict['#d2b48c'] = 'gold'
        color_dict['#008080'] = 'green'
        color_dict['#d8bfd8'] = 'rosa'
        color_dict['#ff6347'] = 'orange'
        color_dict['#40e0d0'] = 'green'
        color_dict['#ee82ee'] = 'violet'
        color_dict['#f5deb3'] = 'gold'
        color_dict['#f5f5f5'] = 'white'
        color_dict['#9acd32'] = 'green'
        #color_dict.pop('#006400')
        color_dict['#f0f8ff'] = 'white'
        color_dict['#faebd7'] = 'beige'
        color_dict['#f0ffff'] = 'white'
        color_dict['#ffe4c4'] = 'beige'
        color_dict['#8a2be2'] = 'purple'
        color_dict['#deb887'] = 'gold'
        color_dict['#484b4d'] = 'black'
        color_dict['#4a4d4e'] = 'black'
        color_dict['#c5c7c6'] = 'white'
        color_dict['#cccecd'] = 'white'
        color_dict['#cfd2d0'] = 'white'
        color_dict['#b1b4b2'] = 'white'
        color_dict['#7e7f7d'] = 'gray'
        color_dict['#2f4f4f'] = 'black'
        color_dict['#0b0b0f'] = 'black'
        color_dict['#b9bab9'] = 'white'
        color_dict['#5f9ea0'] = 'blue'
        color_dict['#7fff00'] = 'green'
        color_dict['#d2691e'] = 'orange'
        color_dict['#ff7f50'] = 'orange'
        color_dict['#6495ed'] = 'blue'
        color_dict['#fff8dc'] = 'beige'
        color_dict['#dc143c'] = 'red'
        color_dict['#008b8b'] = 'cyan'
        color_dict['#b8860b'] = 'gold'
        color_dict['#a9a9a9'] = 'silver'
        #color_dict['#006400'] = 'green'
        color_dict['#bdb76b'] = 'gold'
        color_dict['#8b008b'] = 'purple'
        color_dict['#556b2f'] = 'green'
        color_dict['#ff8c00'] = 'orange'
        color_dict['#9932cc'] = 'purple'
        color_dict['#8b0000'] = 'red'
        color_dict['#e9967a'] = 'orange'
        color_dict['#8fbc8f'] = 'green'
        color_dict['#00ced1'] = 'cyan'
        color_dict['#9400d3'] = 'purple'
        color_dict['#ff1493'] = 'red'
        color_dict['#696969'] = 'gray'
        color_dict['#b22222'] = 'red'
        color_dict['#fffaf0'] = 'white'
        color_dict['#228b22'] = 'green'
        color_dict['#ff00ff'] = 'purple'

    return color_dict

def imageBlur(image):
    imgray = cv2.cvtColor(image,cv2.COLOR_BGR2GRAY)

    blur = cv2.blur(imgray,(10,10))
    return imgray, blur

def imageThresh(blur):
    ret, thresh = cv2.threshold(blur, 127,255, cv2.THRESH_BINARY_INV+cv2.THRESH_OTSU)
    return thresh

def imageContours(thresh, imgray):
    cnts, hier = cv2.findContours(thresh, cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    mask = np.zeros(imgray.shape,np.uint8)
    cv2.drawContours(mask,cnts,-1,255,-1)
    contoursPoints = np.transpose(np.nonzero(mask))
    return contoursPoints

def rgbContoursValue(contoursPoints,image):
    rgb_values = []
    for i in range(len(contoursPoints)):
        coord = contoursPoints[i]
        color = image[coord[0], coord[1]]
        rgb_values.append(color)
    return rgb_values

def RGB2HEX(color):
    return "#{:02x}{:02x}{:02x}".format(int(color[0]), int(color[1]), int(color[2]))

color_dict = colouDict()
def get_colour_name(rgb_triplet):
    min_colours = {}
    for key, name in color_dict.items():
        r_c, g_c, b_c = webcolors.hex_to_rgb(key)
        rd = (r_c - rgb_triplet[0]) ** 2
        gd = (g_c - rgb_triplet[1]) ** 2
        bd = (b_c - rgb_triplet[2]) ** 2
        min_colours[(rd + gd + bd)] = name
         
    return min_colours[min(min_colours.keys())]


def getKeysByValue(dictOfElements, valueToFind):
    listOfKeys = list()
    listOfItems = dictOfElements.items()
    for item  in listOfItems:
        if item[1] == valueToFind:
            listOfKeys.append(item[0])
    return  listOfKeys

def getValuePercentage(dictio):
    new_dict ={}
    s = sum(dictio.values())
    for k, v in dictio.items():
        pct = v * 100.0 / s
        pct = round(pct, 2)
        new_dict[k] = pct
        #new_dict = sorted(dictio.items(), key=operator.itemgetter(1))
    return new_dict

def getColour(d):
   
    v=list(d.values())
    k=list(d.keys())
    colourvalue = v[v.index(max(v))]
    if colourvalue >40:
        colourvalue = k[v.index(max(v))]
    elif colourvalue<40:
        colourvalue = 'colorful'
    return colourvalue



def getDominantColour(image):
    #colour_name = []
    number_of_colors = 10
    a = {}
    ColourPalette = {}

    blur, imgay = imageBlur(image)
    thresh = imageThresh(blur)

    imCnts = imageContours(thresh, imgay)
    rgbValues = rgbContoursValue(imCnts, image)

    
    
    clf = KMeans(n_clusters = number_of_colors)

    labels = clf.fit_predict(rgbValues)
    counts = Counter(labels)
    
    values = list(counts.values())
    #keys = list(counts.keys())

    center_colors = clf.cluster_centers_
    
    ordered_colors = [center_colors[i] for i in counts.keys()]
    
    #hex_colors = [RGB2HEX(ordered_colors[i]) for i in counts.keys()]
    
    colors_name = [get_colour_name(ordered_colors[i]) for i in counts.keys()]
    
    #rgb_colors = [ordered_colors[i] for i in counts.keys()]
    
    d = list(zip(values, colors_name))
    
    for i in range(len(d)):
        a[d[i][0]] = d[i][1]
        b = a.copy()
        
    for value in b.values():
        my_list = getKeysByValue(a, value)
        new_value = sum(my_list)
        ColourPalette[value] = new_value
    ColourPalette = getValuePercentage(ColourPalette)
    dominant_colour = getColour(ColourPalette)
    
    return dominant_colour, ColourPalette

