//
//  ChannelParser.m
//  GuiaTv
//
//  Created by Jordi Coscolla on 21/02/12.
//  Copyright 2012 __MyCompanyName__. All rights reserved.
//

#import "ChannelParser.h"
#import "ChannelModel.h"
@implementation ChannelParser
@synthesize currentChannelId, currentChannelDesc;

- (void)dealloc
{
}

- (NSMutableArray*)start
{
    channels = [[NSMutableArray alloc] initWithCapacity: 10];
    
    NSURL *url = [[NSURL alloc] initWithString:@"http://localhost:9000/channels"];
    xmlParser = [[NSXMLParser alloc] initWithContentsOfURL:url];
    [xmlParser setDelegate:self];
    [xmlParser setShouldProcessNamespaces:NO];
    [xmlParser setShouldReportNamespacePrefixes:NO];
    [xmlParser setShouldResolveExternalEntities:NO];
    [xmlParser parse];
    
    return channels;
    
}


#pragma mark -
#pragma mark NSXMLParserDelegate methods

- (void)parserDidStartDocument:(NSXMLParser *)parser 
{
    NSLog(@"Document started", nil);

}

- (void)parser:(NSXMLParser *)parser parseErrorOccurred:(NSError *)parseError 
{
    NSLog(@"Error: %@", [parseError localizedDescription]);
}

- (void)parser:(NSXMLParser *)parser didStartElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName attributes:(NSDictionary *)attributeDict
{
    currentElementName = elementName;
     if( [elementName isEqualToString: @"channel"])
     {
         currentChannelId = [attributeDict objectForKey: @"id"];
     }
     if( [elementName isEqualToString:@"display-name"])
     {
         currentChannelDesc = [[NSMutableString alloc] init];
     }
}

- (void)parser:(NSXMLParser *)parser didEndElement:(NSString *)elementName namespaceURI:(NSString *)namespaceURI qualifiedName:(NSString *)qName
{
    if([elementName isEqualToString: @"channel"])
    {
        ChannelModel* channel = [[ChannelModel alloc] initChannelWithId:currentChannelId AndDesc:currentChannelDesc];
        [channels addObject:channel];
    }
}        

- (void)parser:(NSXMLParser *)parser foundCharacters:(NSString *)chars
{
    if ([currentElementName isEqualToString:@"display-name"]) 
    {
        [currentChannelDesc appendString:chars];
    } 
}

- (void)parserDidEndDocument:(NSXMLParser *)parser 
{
    NSLog(@"Document finished", nil);
}


@end
