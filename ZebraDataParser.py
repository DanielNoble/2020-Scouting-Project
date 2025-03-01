import requests
import csv
import datetime

baseURL = 'https://www.thebluealliance.com/api/v3/'
header = {'X-TBA-Auth-Key':'INSERT KEY HERE'}

#This prevents us from repeatedly opening and closing a socket + speeds it up.
s = requests.Session()

baseGlobal = 2
counterMaxGlobal = 35

def getTBA(url):
    #allows us to quickly call TBA api endpoints.
    return s.get(baseURL + url, headers=header).json()


class dataInput:

    #is constantly updated with data from the main functions so it can be written to the CSV.
    xsData = ''
    ysData = ''
    matchTime = 0
    alliance = "red"
    matchNumber = 1
    aliPos = 0


def matchWriter(dataInput, matchData, writer):

    #"cleaner" way to write to the CSV file, instead of having this everywhere.
    #Calls the dataInput class to get the specific data to write, along with matchData to get other key info along with writer to write.
    dataInput.matchTime = round(dataInput.matchTime, 1) 
    writer.writerow([int(matchData['alliances'][dataInput.alliance][dataInput.aliPos]['team_key'].strip("frc")), dataInput.matchNumber, dataInput.alliance, dataInput.matchTime, dataInput.xsData, dataInput.ysData]) #writes to the CSV
    dataInput.matchTime = dataInput.matchTime + .1
    timeReturn = dataInput.matchTime
    return timeReturn
    

def matchList(event):

    #Collects and finds the last qual match to be played.
    matches = []
    
    #cycles through every mactch in the list. If its a quals match, it gets tested to see if it was played. If it was, it gets added to the list.
    for match in getTBA("event/" + event + "/matches/simple"):
        if match['comp_level'] == "qm":
            if match['alliances']['red']['score'] == -1 or match['alliances']['red']['score'] == None:
                print("match " + str(match['match_number']) + " has not started")
            else:
                matches.append(int(match['match_number']))
    return max(matches)



def JSONToCSV(event):

    #JSONToCSV does as it says, takes the JSON output to a CSV file. To use with Tableau, you need an XLSX file though.

    #These set the class we call from, along with setting some base values for everything.
    d = dataInput()
    qualVal = matchList(event)
    d.aliPos = 0 
    d.alliance = "red" 
    d.matchTime = 0
    d.matchNumber = 1
    print("collecting match data")

    #Create a CSV file with the name based on the event given, and acts as if as its a new file, overwriting anything that may of been there before.
    with open(event + 'ZebraData.csv', 'w', newline='') as csvFile:
        #we create an easy way to write to the CSV, then define what our column names are, along with creating the column names. 
        writer = csv.writer(csvFile)
        writer.writerow(['Team', 'Match', 'Alliance', 'Time', 'X', 'Y']) 

        #while we are under the total number of quals, the script will run.
        while d.matchNumber < qualVal + 1:
            #Tells the user the match number, then grabs that specific match.
            print(d.matchNumber)
            matchData = getTBA("match/" + event + "_qm" + str(d.matchNumber) + "/zebra_motionworks") #calls a match.

            #Failsafe condition, basically if there's no Zebradata for a match we skip it.
            if matchData == None:
                print("match " + str(d.matchNumber) + " has no Zebra data, skipping.")
                d.matchNumber+=1
                continue
            
            #if aliPos hits 3, no team will exist (count from 0), so we leave the loop
            while d.aliPos < 3:
                
                #parses the JSON to find designated team, then the specific X/Y data. I could probably combine these...
                allianceXsData = matchData['alliances'][d.alliance][d.aliPos]
                allianceYsData = matchData['alliances'][d.alliance][d.aliPos]
                teamXsData = allianceXsData['xs']
                teamYsData = allianceYsData['ys'] 

                #TeamXsData and teamYsData are two long lists of data, this calls each row both the both of them.
                for xsData, ysData in zip(teamXsData, teamYsData):
                    
                    #The data (should) only be two things: a float or a null. So we check to see if its either, then write the respective code.
                    if type(xsData) == float: 
                        d.xsData = xsData
                        d.ysData = ysData
                        d.matchTime = matchWriter(d, matchData, writer)
                    elif xsData == None: 
                        d.xsData = "null"
                        d.ysData = "null"
                        d.matchTime = matchWriter(d, matchData, writer)
                    #Mainly for debug. If youre hitting this, something obviously went wrong.
                    else:
                        d.matchTime = round(d.matchTime, 1)
                        d.matchTime = d.matchTime + .1
                        print("Something was wrong with the data given.")
                        
                #We incriment the alliance posiiton then set matchTime to 0 to start next match.     
                d.aliPos = d.aliPos + 1
                d.matchTime = 0

                #logic to switch between red and blue alliances.
                if d.aliPos == 3 and d.alliance == "red":
                    d.alliance = "blue"
                    d.aliPos = 0
                elif d.aliPos == 3 and d.alliance == "blue":
                    d.alliance = "red"

            #Once all teams in a match are run through, we reset Alliance Position and time, while incrimenting the match number.                
            d.aliPos = 0
            d.matchTime = 0
            d.matchNumber= d.matchNumber + 1

        #when we're done, we tell the user its finished. Can be misleading though if some matches are skipped.    
        print(str(d.matchNumber - 1) + " matches have been saved")


def JSONToCSVAutos(event):

    #Takes the first 16 seconds of data (accounts for variance in start times) to look at auto paths without having to worry about the rest of the data.
    
    #These set the class we call from, along with setting some base values for everything.
    d = dataInput()
    qualVal = matchList(event)
    d.aliPos = 0 
    d.alliance = "red" 
    d.matchTime = 0
    d.matchNumber = 1
    print("collecting match data")

    #Create a CSV file with the name based on the event given, and acts as if as its a new file, overwriting anything that may of been there before.
    with open(event + 'ZebraDataAutos.csv', 'w', newline='') as csvFile:
        
        #we create an easy way to write to the CSV, then define what our column names are, along with creating the column names.  
        writer = csv.writer(csvFile)
        writer.writerow(['Team', 'Match', 'Alliance', 'Time', 'X', 'Y'])

        #while we are under the total number of quals, the script will run.
        while d.matchNumber < qualVal + 1:
            
            #Tells the user the match number, then grabs that specific match.
            print(d.matchNumber)
            matchData = getTBA("match/" + event + "_qm" + str(d.matchNumber) + "/zebra_motionworks") #calls a match.

            #Failsafe condition, basically if there's no Zebradata for a match we skip it.
            if matchData == None:
                print("match " + str(d.matchNumber) + " has no Zebra data, skipping.")
                d.matchNumber+=1
                continue
            
            #if aliPos hits 3, no team will exist (count from 0), so we leave the loop
            while d.aliPos < 3:
                
                #parses the JSON to find designated team, then the specific X/Y data. I could probably combine these...
                allianceXsData = matchData['alliances'][d.alliance][d.aliPos]
                allianceYsData = matchData['alliances'][d.alliance][d.aliPos]
                teamXsData = allianceXsData['xs']
                teamYsData = allianceYsData['ys']

                #TeamXsData and teamYsData are two long lists of data, this calls each row both the both of them.
                for xsData, ysData in zip(teamXsData, teamYsData):
                    if d.matchTime < 16.1:
                        #The data (should) only be two things: a float or a null. So we check to see if its either, then write the respective code.
                        if type(xsData) == float: 
                            d.xsData = xsData
                            d.ysData = ysData
                            d.matchTime = matchWriter(d, matchData, writer)
                        elif xsData == None:
                            d.xsData = "null"
                            d.ysData = "null"
                            d.matchTime = matchWriter(d, matchData, writer)
                        #Mainly for debug. If youre hitting this, something obviously went wrong.
                        else: 
                             d.matchTime = round(d.matchTime, 1)
                             d.matchTime = d.matchTime + .1
                             print("Something was wrong with the data given.")
                        
                #We incriment the alliance posiiton then set matchTime to 0 to start next match.     
                d.aliPos = d.aliPos + 1
                d.matchTime = 0

                #logic to switch between red and blue alliances.
                if d.aliPos == 3 and d.alliance == "red":
                    d.alliance = "blue"
                    d.aliPos = 0
                elif d.aliPos == 3 and d.alliance == "blue":
                    d.alliance = "red"

            #Once all teams in a match are run through, we reset Alliance Position and time, while incrimenting the match number.                
            d.aliPos = 0
            d.matchTime = 0
            d.matchNumber= d.matchNumber + 1

        #when we're done, we tell the user its finished. Can be misleading though if some matches are skipped.    
        print(str(d.matchNumber - 1) + " matches have been saved")

def findShooterSpots(event):

    global baseGlobal, counterMaxGlobal

    #These set the class we call from, along with setting some base values for everything.
    d = dataInput()
    qualVal = matchList(event)
    d.aliPos = 0 
    d.alliance = "red" 
    d.matchTime = 0
    d.matchNumber = 1
    b, c = baseGlobal, counterMaxGlobal
    base = b
    print("collecting match data")
    #counter is used to count how long we wait until we save the data. Currently, 10 equals one second.
    counter = 0
    counterMax = c

    #Create a CSV file with the name based on the event given, and acts as if as its a new file, overwriting anything that may of been there before.
    with open(event + 'ZebraShooterLocation.csv', 'w', newline='') as csvFile:

        #we create an easy way to write to the CSV, then define what our column names are, along with creating the column names.
        writer = csv.writer(csvFile)
        writer.writerow(['Team', 'Match', 'Alliance', 'Time', 'X', 'Y'])

        #while we are under the total number of quals, the script will run.
        while d.matchNumber < qualVal + 1:

            #we create an easy way to write to the CSV, then define what our column names are, along with creating the column names. 
            print(d.matchNumber)
            matchData = getTBA("match/" + event + "_qm" + str(d.matchNumber) + "/zebra_motionworks")

            #Failsafe condition, basically if there's no Zebradata for a match we skip it.
            if matchData == None:
                print("match " + str(d.matchNumber) + " has no Zebra data, skipping.")
                d.matchNumber+=1
                continue

            #super high values are put as placeholders so we dont risk them accidentally false flagging data when first called every match.
            #These variables will just play telephone with each other, checking to see if movement has happened.
            #I bet theres a better way to do this, but this was my solution.
            xsTestA = 1257
            xsTestB = 4131
            xsTestC = 15286

            ysTestA = 800
            ysTestB = 800
            ysTestC = 800

            xsRepeatTest = 300
            ysRepeatTest = 300

            #if aliPos hits 3, no team will exist (count from 0), so we leave the loop
            while d.aliPos < 3:
                
                #parses the JSON to find designated team, then the specific X/Y data. I could probably combine these...
                allianceXsData = matchData['alliances'][d.alliance][d.aliPos]
                allianceYsData = matchData['alliances'][d.alliance][d.aliPos]
                teamXsData = allianceXsData['xs']
                teamYsData = allianceYsData['ys']

                #TeamXsData and teamYsData are two long lists of data, this calls each row both the both of them.
                for xsData, ysData in zip(teamXsData, teamYsData): 

                    #Our whacky game of telephone begins, sending data up the chain to start comparing.
                    xsTestC = xsTestB
                    xsTestB = xsTestA
                    xsTestA = xsData

                    ysTestC = ysTestB
                    ysTestB = ysTestA
                    ysTestA = ysData

                    #We check if any value is null, since obviously tracking a null value shot gets us nowehere. If null, we move on.
                    if xsData == None or ysData == None or xsTestC == None or ysTestC == None:
                        counter = 0
                        d.matchTime = d.matchTime + .1

                    #We check if the data is within range, currently .15 on both X and Y axis. If it is, we start counting.
                    elif xsData - xsTestC < .15 and ysData - ysTestC < .15:
                        counter = counter + 1
                        d.matchTime = d.matchTime + .1

                        #When the counter hits one, we save the location and time,rounding it for easy tracking later. Currently, this value (bsae) is 3ft. 
                        if counter == 1:
                            xsDataWrite = base * round(xsData/base)
                            ysDataWrite = base * round(ysData/base)
                            timeAtStart = d.matchTime

                    #If the values at any point leave the .15 threshold, we reset the counter and start again.
                    else:
                        counter = 0
                        d.matchTime = d.matchTime + .1

                    #Once the counter hits the threshold, defaulting to 3.5s, we start writing.
                    if counter > counterMax:

                        #we check to make sure theyre not still in the same spot.
                        #While they may just take that long to shoot, chances are theyre instead dead on the field.
                        if xsRepeatTest == xsDataWrite and ysRepeatTest == ysDataWrite:
                            counter = 0
                        #If it doesnt match, we set the data up to be written, and reset the counter.
                        else:
                            xsRepeatTest = xsDataWrite
                            ysRepeatTest = ysDataWrite
                            d.xsData = xsDataWrite
                            d.ysData = ysDataWrite
                            d.matchTime = matchWriter(d, matchData, writer)
                            counter = 0
                    
                #We incriment the alliance posiiton then set matchTime to 0 to start next match.     
                d.aliPos = d.aliPos + 1
                d.matchTime = 0

                #logic to switch between red and blue alliances.
                if d.aliPos == 3 and d.alliance == "red":
                    d.alliance = "blue"
                    d.aliPos = 0
                elif d.aliPos == 3 and d.alliance == "blue":
                    d.alliance = "red"

            #Once all teams in a match are run through, we reset Alliance Position and time, while incrimenting the match number.                
            d.aliPos = 0
            d.matchTime = 0
            d.matchNumber= d.matchNumber + 1
        print(str(d.matchNumber - 1) + " matches have been saved")
                        

def mainMenu():

    global baseGlobal, counterMaxGlobal

    #This will be where the user will choose what they want to do.
    
    WRITEFULLDATA = 1
    WRITEAUTODATA = 2
    WRITESHOTLOCATION = 3
    SETTINGS = 4
    HOWTO = 5
    QUIT = 0

    choice = 4513

    while choice != QUIT:
        displayMenu()

        try:
            choice = int(input("Please choose an option from the menu: "))
        except ValueError:
            print("\n")
            print("Please use the numbers provided to select a menu option.")
            
        if choice == WRITEFULLDATA:
            event = input("Enter event code: ")
            eventExceptionTest = "match/" + event + "_qm1"
            try:
                if getTBA(eventExceptionTest)['alliances']['red']['score'] == -1 or getTBA(eventExceptionTest)['alliances']['red']['score'] == None:
                    g=g
            except:
                print ("\n")
                print("Error:")
                print("No match data has been found for this event. Has the event started?")
                print ("\n")
            else:
                JSONToCSV(event) 

        elif choice == WRITEAUTODATA:
            event = input("Enter event code: ")
            eventExceptionTest = "match/" + event + "_qm1"
            try:
                if getTBA(eventExceptionTest)['alliances']['red']['score'] == -1 or getTBA(eventExceptionTest)['alliances']['red']['score'] == None:
                    g=g
            except:
                print ("\n")
                print("Error:")
                print("No match data has been found for this event. Has the event started?")
                print ("\n")
            else:
                JSONToCSVAutos(event) 

        elif choice == WRITESHOTLOCATION:
            event = input("Enter event code: ")
            eventExceptionTest = "match/" + event + "_qm1"
            try:
                if getTBA(eventExceptionTest)['alliances']['red']['score'] == -1 or getTBA(eventExceptionTest)['alliances']['red']['score'] == None:
                    g=g
            except:
                print ("\n")
                print("Error:")
                print("No match data has been found for this event. Has the event started?")
                print ("\n")
            else:
                findShooterSpots(event)

        elif choice == SETTINGS:
            baseGlobal, counterMaxGlobal = settingsMenu(baseGlobal, counterMaxGlobal)

        elif choice == HOWTO:
            tutorial()

        else:
            print("A valid choice was not selected. Please try again.")
            print("\n")

def displayMenu():

    #having this as its own function makes it nicer to edit in the long run.
    
    print("Zebra Parser for Excel/Tableau" + "\n")
    print("Menu:")
    print("1. save an event's Zebra data")
    print("2. Save only an event's auto Zebra data")
    print("3. Save shooter locations based on Zebra data")
    print("4. Change shooter location settings")
    print("5. Explain how the program works")
    print("0. quit")
    print("\n")

def settingsMenu(baseGlobal, counterMaxGlobal):

    print("\n")
    print("Please note that you will have to change these values every time you start the program.")
    print("A future update before Week 1 events should fix this, but if it hasnt, please bug me about it.")
    print("\n")

    BASEVALUE = 1
    COUNTERMAXVALUE = 2
    GOBACK = 0

    print("The rounding for shooter location recording is " + str(baseGlobal) + " feet.")
    print("The time it takes for a location to be reorded is " + str(counterMaxGlobal/10) + " seconds.")

    print("1. Change the rounding value")
    print("2. Change the time value")
    print("0. Go back to the main menu")
    print("\n")

    settingChoice = int(input("Please select which value you want to change: "))

    while settingChoice != GOBACK:
        if settingChoice == BASEVALUE:
            try:
                baseGlobal = int(input("Enter new rounding value: "))
            except:
                baseGlobal = 3
                print("Invalid entry. If a decimal was tried, please note that they are currently not supported for this value.")
                return(baseGlobal, counterMaxGlobal)
        elif settingChoice == COUNTERMAXVALUE:
            try:
                counterMaxGlobal = str(input("Enter new time value: "))
                counterMaxGlobal = int(float(counterMaxGlobal) * 10)
                print("The time it takes for a location to be reorded is " + str(counterMaxGlobal/10) + " seconds.")
                print(baseGlobal)
                print(counterMaxGlobal)
                print("\n")
                return(baseGlobal, counterMaxGlobal)
            except:
                counterMaxGlobal = 35
                print("Invalid entry.")
        else:
            continue
            
    return baseGlobal, counterMaxGlobal

def tutorial():
    
    print("\n")
    print("\n")
    print("\n")
    print("Tutorial:")
    print("\n")
    print("This program is made to take Zebra JSON outputs and form them in such a way that makes them useful for Excel and Tableau use.")
    print("Each of the main options will ask for an event code. You can find these in the URL of every events page.")
    print("For example, if you wanted data from 2020 PNW West Valley District Event, the event code would be '2020waspo'.")
    print("Currently, there are three different options for what data to collect:")
    print("\n")
    print("1. Full match Zebra Data: Collect all zebra data from an event and put it into one file.")
    print("These will get fairly large for an excel file (20mb) and may cause loading issues with tableau on weaker PC's.")
    print("\n")
    print("2. Zebra Auto Data: This will collect the first 16 seconds of every match and save them into one file.")
    print("This is great for finding paths of teams that you will be competing with/against at an event.")
    print("\n")
    print("3. Zebra Shooter Location: This will comb through the data to find where teams are consistently in one spot, and save those locations.")
    print("The default time is 3.5s in one spot, with each location value rounded to the nearest 3ft mark. These can be edited in Settings.")
    print("\n")
    print("Be aware that all of these are saved as CSV files, and need to be resaved as an XLSX file to be used in Tableau.")
    print("\n")
    
        
    


try:
    if 'Error' in getTBA('status') or header == {'X-TBA-Auth-Key':''} or header == {'X-TBA-Auth-Key':'EDIT ME!'}:
        g = g
except:
    print("Error:")
    print("No TBA API key was found or the key was incorrectly entered. Double check your TBA API key, or create one at http://www.thebluealliance.com/account.")
    s.close()
else:
    mainMenu()
    s.close()
